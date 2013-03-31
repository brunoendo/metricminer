package org.metricminer.controller;

import java.util.List;

import org.metricminer.builder.ProjectBuilder;
import org.metricminer.config.MetricMinerConfigs;
import org.metricminer.infra.dao.ProjectDao;
import org.metricminer.infra.dao.TagDao;
import org.metricminer.infra.interceptor.LoggedUserAccess;
import org.metricminer.model.Author;
import org.metricminer.model.Project;
import org.metricminer.model.Tag;
import org.metricminer.ui.TagTokenizer;
import org.metricminer.worker.InitialTasks;
import org.metricminer.worker.Worker;
import org.metricminer.worker.http.Response;
import org.metricminer.worker.http.Status;
import org.metricminer.worker.representations.TaskRepresentation;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class ProjectController {

    private final Result result;
    private final ProjectDao dao;
    private final MetricMinerConfigs configs;
    private final TagTokenizer tokenize;
    private final TagDao tagDao;
	private final InitialTasks initialTasks;
	private final Worker worker;
    
    public ProjectController(Result result, ProjectDao dao, TagDao tagDao,
            MetricMinerConfigs metricMinerConfigs, TagTokenizer tokenize, 
            InitialTasks initialTasks, Worker worker) {
        this.result = result;
        this.dao = dao;
        this.tagDao = tagDao;
        this.configs = metricMinerConfigs;
        this.tokenize = tokenize;
		this.initialTasks = initialTasks;
		this.worker = worker;
    }

    @LoggedUserAccess
    @Get("/projects/new")
    public void form() {
        result.include("metrics", configs.getRegisteredMetrics());
    }

    @Get("/projects/{page}")
    public void list(int page) {
        result.include("projects", dao.listPage(page));
        result.include("totalPages", dao.totalPages());
        result.include("currentPage", page);
    }

    @Get("/project/{id}")
    public void detail(Long id) {
        Project project = dao.findProjectBy(id);
        List<Author> commiters = dao.commitersFor(project);
        result.include("tags", tokenize.tags(project.getTags()));
        result.include("commiters", commiters);
        result.include("project", project);	
    }
    
    @Get("/projects/{id}/commitChartData")
    public void commitChartData(Long id) {
        Project project = dao.findProjectBy(id);
        result.include("lastSixMonthsCommitCountMap", dao.commitCountForLastMonths(project));
    }
    
    @Get("/projects/{id}/fileCountChartData")
    public void fileCountChartData(Long id) {
        Project project = dao.findProjectBy(id);
        result.include("fileCountPerCommit", dao.fileCountPerCommitForLastSixMonths(project));
    }

    @LoggedUserAccess
    @Get("/project/{projectId}/delete")
    public void deleteProject(Long projectId) {
        dao.delete(projectId);
        result.redirectTo(ProjectController.class).list(1);
    }

    @LoggedUserAccess
    @Post("/projects/{projectId}/tags/remove")
    public void removeTag(Long projectId, String tagName) {
        Project project = dao.findProjectBy(projectId);
        project.removeTag(tagName);
        dao.update(project);

        result.nothing();
    }

    @LoggedUserAccess
    @Post("/projects/{projectId}/tags/")
    public void addTag(Long projectId, String tagName) {
        Tag tag = tagDao.byName(tagName);
        if (tag == null) {
            tag = new Tag(tagName);
            tagDao.save(tag);
        }
        Project project = dao.findProjectBy(projectId);
        project.addTag(tag);
        dao.update(project);

        result.nothing();
    }

    @LoggedUserAccess
    @Post("/projects")
    public void createProject(String name, String scmUrl) {
        Project project = new Project();
        project.setName(name);
        project.setScmUrl(scmUrl);
        saveProject(project);
        result.redirectTo(this).addTasks(project.getId());
    }
    
    @LoggedUserAccess
    @Get("/project/{projectId}/tasks")
    public void addTasks(Long projectId) {
		List<TaskRepresentation> tasksFor = initialTasks.tasksFor(projectId);
		for (TaskRepresentation taskRepresentation : tasksFor) {
			Response sendTask = worker.sendTask(taskRepresentation);
			Status status = sendTask.status();
			if (!status.equals(Status.Code.CREATED)) {
				throw new IllegalStateException("Could not create task");
			}
		}
		result.redirectTo(this).list(1);
    }
    
    @Post("/projects/06560fb292075c5eeca4ceb586185332")
    public void createProjectRemote(String name, String scmUrl) {
        Project project = new Project();
        project.setName(name);
        project.setScmUrl(scmUrl);
    	saveProject(project);
    }

	private void saveProject(Project project) {
	    Project completeProject = new ProjectBuilder()
	        .withBaseProject(project)
	        .withConfigs(configs).build();
        dao.save(completeProject, configs);
	}

}
