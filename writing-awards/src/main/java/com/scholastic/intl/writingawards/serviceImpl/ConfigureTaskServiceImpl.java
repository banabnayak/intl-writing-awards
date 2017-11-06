/**
 * 
 */
package com.scholastic.intl.writingawards.serviceImpl;

import javax.inject.Inject;

import com.scholastic.intl.writingawards.dao.impl.TaskDaoImpl;
import com.scholastic.intl.writingawards.entity.Task;
import com.scholastic.intl.writingawards.helper.ServiceHelper;
import com.scholastic.intl.writingawards.model.ConfigureTaskVO;
import com.scholastic.intl.writingawards.service.ConfigureTaskService;

/**
 * @author madhusmita.nayak
 * 
 */
public class ConfigureTaskServiceImpl implements ConfigureTaskService {
	/**
	 * Inject DAO implementation.
	 */
	@Inject
	TaskDaoImpl configureTaskDaoImpl;
	/**
	 * Inject Service Helper
	 */
	@Inject
	ServiceHelper serviceHelper;

	@Override
	public Task saveOrUpdateTask(ConfigureTaskVO configureTaskVO) {
		boolean status = false;
		Task task;
		if (configureTaskVO.getTaskId() != 0) {
			task = serviceHelper.setUpdateTask(configureTaskVO);
			task = configureTaskDaoImpl.updateTask(task);
		} else {
			task = serviceHelper.setTaskEntity(configureTaskVO);
			task = configureTaskDaoImpl.persistTask(task);
		}
		return task;
	}

	@Override
	public ConfigureTaskVO getSubmittedTask(Long taskId) {
		ConfigureTaskVO configureTaskVO = null;
		if (taskId != 0) {
			Task getTask = configureTaskDaoImpl.getTaskById(taskId);
			if (getTask != null) {
				configureTaskVO = serviceHelper.getTaskVO(getTask);
			}
		}

		return configureTaskVO;
	}

	@Override
	public boolean removeTask(ConfigureTaskVO configureTaskVO) {
		boolean status = false;
		return status;
	}

}
