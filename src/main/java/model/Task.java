package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("rawtypes")
@Entity
public class Task implements Comparable {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Project project;
    private String name;
    private Class runnableTaskFactoryClass;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar submitDate;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private Integer position;
    @OneToMany
    private List<Task> depends;
    @OneToMany(mappedBy = "task")
    private List<TaskConfigurationEntry> configurationEntries;

    public Task() {
        this.depends = new ArrayList<Task>();
        this.configurationEntries = new ArrayList<TaskConfigurationEntry>();
    }

    public Task(Project project, String name, Class runnableTaskFactoryClass, Integer position) {
        this();
        this.project = project;
        this.name = name;
        this.runnableTaskFactoryClass = runnableTaskFactoryClass;
        this.submitDate = new GregorianCalendar();
        this.status = TaskStatus.QUEUED;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public Class getRunnableTaskFactoryClass() {
        return runnableTaskFactoryClass;
    }

    public Project getProject() {
        return project;
    }

    public void start() {
        this.status = TaskStatus.STARTED;
    }

    public void finish() {
        this.status = TaskStatus.FINISHED;
    }

    public void fail() {
        this.status = TaskStatus.FAILED;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Calendar getSubmitDate() {
        return submitDate;
    }

    @Override
    public int compareTo(Object o) {
        return ((Task) o).getPosition().compareTo(this.position);
    }

    public Integer getPosition() {
        return position;
    }

    public void addDependency(Task task) {
        if (depends == null)
            depends = new ArrayList<Task>();
        depends.add(task);
    }

    public boolean isDependenciesFinished() {
        for (Task depencyTask : depends) {
            if (!depencyTask.isFinished())
                return false;
        }
        return true;
    }

    private boolean isFinished() {
        return this.status == TaskStatus.FINISHED;
    }

    public void addTaskConfigurationEntry(String key, String value) {
        configurationEntries.add(new TaskConfigurationEntry(key, value, this));
    }

}
