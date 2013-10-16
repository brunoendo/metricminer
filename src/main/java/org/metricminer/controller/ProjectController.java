package org.metricminer.controller;

import java.util.List;

import org.metricminer.builder.ProjectBuilder;
import org.metricminer.config.MetricMinerConfigs;
import org.metricminer.infra.dao.ProjectDao;
import org.metricminer.infra.dao.TagDao;
import org.metricminer.infra.dao.dto.AuthorAndCommitCount;
import org.metricminer.infra.interceptor.LoggedUserAccess;
import org.metricminer.model.Author;
import org.metricminer.model.Project;
import org.metricminer.model.Tag;
import org.metricminer.ui.TagTokenizer;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;

@Resource
public class ProjectController {

    private final Result result;
    private final ProjectDao dao;
    private final MetricMinerConfigs configs;
    private final TagTokenizer tokenize;
    private final TagDao tagDao;
	private Validator validator;
    
    public ProjectController(Result result, ProjectDao dao, TagDao tagDao,
            MetricMinerConfigs metricMinerConfigs, TagTokenizer tokenize, 
            Validator validator) {
        this.result = result;
        this.dao = dao;
        this.tagDao = tagDao;
        this.configs = metricMinerConfigs;
        this.tokenize = tokenize;
		this.validator = validator;
    }

    @LoggedUserAccess
    @Get("/projects/new")
    public void form() {
        result.include("metrics", configs.getRegisteredMetrics());
    }

    @Get("/projects/{page}")
    public void list(int page) {
        result.include("projects", dao.findAll(page));
        result.include("totalPages", dao.totalPages());
        result.include("currentPage", page);
    }

    @Get("/projects/search")
    public void search(String criteria) {
    	if(criteria == null || criteria.isEmpty()) {
    		result.redirectTo(this).list(1);
    		return;
    	}
    	
    	result.include("projects", dao.search(criteria));
    	result.include("totalPages", 1);
    	result.include("currentPage", 1);
    	
    	result.use(Results.page()).of(ProjectController.class).list(1);
    }

    @Get("/project/{id}")
    public void detail(Long id) {
        Project project = dao.findProjectBy(id);
        List<AuthorAndCommitCount> commiters = dao.commitersFor(project, 10);
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
    	if (name == null || name.isEmpty()) {
    		validator.add(new ValidationMessage("The name of the project cannot be empty", "error"));
    		result.include("scmUrl", scmUrl);
    	}
    	if (scmUrl == null || scmUrl.isEmpty()) {
    		validator.add(new ValidationMessage("The SCM url cannot be empty", "error"));
    		result.include("name", name);
    	}
    	
    	validator.onErrorRedirectTo(this).form();
    	
        Project project = new Project();
        project.setName(name);
        project.setScmUrl(scmUrl);
        saveProject(project);
    }
    
    @Post("/projects/06560fb292075c5eeca4ceb586185332")
    public void createProjectRemote(String name, String scmUrl) {
        createProject(name, scmUrl);
    }

	private void saveProject(Project project) {
	    Project completeProject = new ProjectBuilder()
	        .withBaseProject(project)
	        .withInitialTasks()
	        .withConfigs(configs).build();
        dao.save(completeProject, configs);
        
        result.include("added", true);
        result.redirectTo(ProjectController.class).list(1);
	}

}
