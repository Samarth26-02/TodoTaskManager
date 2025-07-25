package com.todo.controller;

import com.todo.dto.TaskDTO;
import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/listTasks")
public class TaskListServlet extends HttpServlet{
	private TaskService service = new TaskServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
		
		List<TaskDTO> tasks = service.getAllTasks();
		
		req.setAttribute("tasks",tasks);
		req.getRequestDispatcher("tasks.jsp").forward(req, resp);
	}
}