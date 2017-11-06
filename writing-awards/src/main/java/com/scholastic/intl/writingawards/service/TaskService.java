package com.scholastic.intl.writingawards.service;

import java.util.List;

import com.scholastic.intl.writingawards.entity.Task;

public interface TaskService {

	public Task addTask(Task task);
	public Boolean removeTask(Long id);
	public List<Task> getAllTasks();
	
}
