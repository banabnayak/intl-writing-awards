/**
 * 
 */
package com.scholastic.intl.writingawards.service;

import com.scholastic.intl.writingawards.entity.Task;
import com.scholastic.intl.writingawards.model.ConfigureTaskVO;

/**
 * @author madhusmita.nayak
 *
 */
public interface ConfigureTaskService {
	public Task saveOrUpdateTask(ConfigureTaskVO configureTaskVO);
	public ConfigureTaskVO getSubmittedTask(Long taskId);
	public boolean removeTask(ConfigureTaskVO configureTaskVO);

}
