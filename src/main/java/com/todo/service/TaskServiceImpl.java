package com.todo.service;

import com.todo.dto.TaskDTO;
import com.todo.repository.TaskRepository;
import java.util.List;

public class TaskServiceImpl implements TaskService{
	
	private TaskRepository repository = new TaskRepository();
	
	@Override 
	public boolean addTask(TaskDTO task) {
		return repository.addTask(task);
	}
	
	@Override
	public List<TaskDTO> getAllTasks() {
		return repository.getAllTasks();
	}
	

	@Override
	public boolean deleteTask(int id) {
	    return repository.deleteTask(id);
	}

	@Override
	public boolean markTaskAsCompleted(int id) {
	    return repository.markAsCompleted(id);
	}

	@Override
	public TaskDTO getTaskById(int id) {
	    return repository.getTaskById(id);
	}

	@Override
	public boolean updateTask(TaskDTO task) {
	    return repository.updateTask(task);
	}

	
}