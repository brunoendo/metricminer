package controller;

import model.Project;
import br.com.caelum.revolution.executor.SimpleCommandExecutor;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import dao.ConfigurationEntryDao;
import dao.ProjectDao;

@Resource
public class ProjectController {

	private final Result result;
	private final ProjectDao dao;
	private final ConfigurationEntryDao configurationEntryDao;

	public ProjectController(Result result, ProjectDao dao,
			ConfigurationEntryDao projectConfigurationEntryDao) {
		this.result = result;
		this.dao = dao;
		this.configurationEntryDao = projectConfigurationEntryDao;
	}

	@Get("/projects/new")
	public void form() {
	}

	@Get("/projects")
	public void list() {
		result.include("projects", dao.listAll());
	}

	@Get("/projects/{id}")
	public void detail(Long id) {
		result.include("project", dao.findProjectBy(id));
	}

	@Post("/projects")
	public void createProject(String name, String scmUrl) {
		Project project = new Project(name, scmUrl);
		dao.save(project);
		configurationEntryDao.saveInitialProjectConfigurations(project);
		result.redirectTo(ProjectController.class).list();
	}

	@Get("/project/{id}/clone")
	public void cloneRepository(Long id) {
		Process proc = null;
		Project project = dao.findProjectBy(id);

		SimpleCommandExecutor executor = new SimpleCommandExecutor();
		String metricMinerHome = "/home/csokol/ime/tcc/MetricMinerHome";
		executor.execute(
				"mkdir -p " + metricMinerHome + "/projects/" + project.getId(),
				"/");
		String output = executor.execute("git clone " + project.getScmUrl(),
				metricMinerHome + "/projects/" + project.getId());

		result.include("project", project);
		result.include("output", output);
	}
}
