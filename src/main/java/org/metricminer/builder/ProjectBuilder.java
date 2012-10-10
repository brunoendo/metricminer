package org.metricminer.builder;

import org.metricminer.config.MetricMinerConfigs;
import org.metricminer.model.Project;

public class ProjectBuilder {
    private String name;
    private String scmUrl;
    private MetricMinerConfigs metricMinerConfigs;
    private String projectPath;
    private boolean withInitialTasks;

    public ProjectBuilder() {
        this.name = "default project name";
        this.scmUrl= "default scmUrl name";
        this.projectPath = "/tmp/path";
    }

    public Project build() {
        Project project = new Project(name, scmUrl, projectPath);
        if (withInitialTasks) 
            project.setupInitialTasks();
        return project;
    }

    public ProjectBuilder withBaseProject(Project p) {
        this.name = p.getName();
        this.scmUrl = p.getScmUrl();
        return this;
    }
    
    public ProjectBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public ProjectBuilder withInitialTasks() {
        this.withInitialTasks = true;
        return this;
    }
    
    public ProjectBuilder withConfigs(MetricMinerConfigs configs) {
        this.projectPath = configs.getRepositoriesDir() + "/projects/";
        return this;
    }
}
