package org.metricminer.controller;

import org.metricminer.infra.dao.TaskDao;
import org.metricminer.tasks.TaskQueueStatus;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

@Resource
public class StatusController {

	private TaskQueueStatus status;
	private final Result result;
	private final TaskDao taskDao;

	public StatusController(TaskQueueStatus status, TaskDao taskDao, Result result) {
		this.status = status;
		this.taskDao = taskDao;
		this.result = result;
	}

	@Get("/status")
	public void showStatus() {
		result.include("status", status);
		result.include("tasks", taskDao.listLast50Tasks());
	}
	
}
