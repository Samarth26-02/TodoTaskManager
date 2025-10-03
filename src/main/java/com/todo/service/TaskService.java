package com.todo.service;

import com.todo.dto.TaskDTO;
import com.todo.repository.TaskRepository;
import java.util.List;

public interface TaskService{
	TaskDTO addTask(TaskDTO task) throws Exception;
	List<TaskDTO> getAllTasks();
	boolean deleteTask(int id);
	boolean markTaskAsCompleted(int id);
	TaskDTO getTaskById(int id);
	boolean updateTask(TaskDTO task);
	
}