/**
 * 
 */
package com.scholastic.intl.writingawards.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.intl.writingawards.entity.Task;

/**
 * @author madhusmita.nayak
 * 
 */
@Stateless
public class TaskDaoImpl extends GenericDAOSupport<Task, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * LOGGER for the current class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskDaoImpl.class);

	public Task updateTask(Task task) {
		Task taskEntity = null;
		try {
			taskEntity = getDAOManager().find(Task.class, task.getId());
			if (taskEntity != null
					&& taskEntity.getDescription().equalsIgnoreCase(task.getDescription())) {
				taskEntity.setUpdatedBy(task.getUpdatedBy());
				taskEntity.setStartDate(task.getStartDate());
				taskEntity.setEndDate(task.getEndDate());
				taskEntity.setDeleted(Boolean.FALSE);
				entityManager.merge(taskEntity);

				LOGGER.info("task removed successfully");
			}

		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}

		return taskEntity;
	}

	public Task persistTask(Task task) {

		Task status = null;
		if (task != null) {
			try {
				// transaction.begin();
				entityManager.persist(task);
				// transaction.commit();
				if (task.getId() != null) {
					status = task;
					LOGGER.info("Task added successfully");

				}
			} catch (Exception exception) {
				if (LOGGER.isErrorEnabled()) {
					LOGGER.error(exception.getMessage());
				}

			}
		}
		return status;
	}

	public Task getTaskById(Long taskId) {
		Task task = null;
		try {
			// transaction.begin();
			task = getDAOManager().find(Task.class, taskId);
			LOGGER.info("Task details retrieved successfully");
		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}
		}

		return task;
	}

	public Boolean removeTask(Long id) {
		boolean status = false;
		try {
			Task taskEntity = getDAOManager().find(Task.class, id);
			if (taskEntity != null) {
				taskEntity.setDeleted(Boolean.TRUE);
				entityManager.merge(taskEntity);
				status = true;
				LOGGER.info("task removed successfully");
			}

		} catch (Exception exception) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(exception.getMessage());
			}

		}

		return status;
	}

	public List<Task> getAllTasks() {

		Query query = getDAOManager().createNamedQuery("getAllTasks");
		List<Task> tasks = query.getResultList();
		return tasks;
	}

	public Task getTaskByDescriptiob(String description) {
		try {

			Query query = getDAOManager().createNamedQuery("getTaskByDesc", Task.class);
			query.setParameter("description", description);
			return (Task) query.getSingleResult();
		} catch (Exception e) {

			LOGGER.info("Problem Dinding Task " + e);
			return null;
		}

	}
}