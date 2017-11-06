package com.scholastic.intl.writingawards.serviceImpl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.dao.impl.TaskDaoImpl;
import com.scholastic.intl.writingawards.entity.Task;
import com.scholastic.intl.writingawards.service.TaskService;

public class TaskServiceImpl implements TaskService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Inject
	TaskDaoImpl taskDAOImpl;

	@Override
	public Task addTask(Task task) {
		if (task != null) {

			task.setDeleted(Boolean.FALSE);
			if (task.getId() == null) {

				Task task1 = taskDAOImpl.getTaskByDescriptiob(task.getDescription());
				if (task1 == null) {
					LOGGER.info(" Task  name does not exists");
					return taskDAOImpl.persistTask(task);
				} else {
					LOGGER.info(" Task  name exists");
					return null;
				}

			} else {
				return taskDAOImpl.updateTask(task);
			}

		} else {
			return null;
		}

	}

	@Override
	public Boolean removeTask(Long id) {
		return taskDAOImpl.removeTask(id);
	}

	@Override
	public List<Task> getAllTasks() {
		return taskDAOImpl.getAllTasks();
	}

}
