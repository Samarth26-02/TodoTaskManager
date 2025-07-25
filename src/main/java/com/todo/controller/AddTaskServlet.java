package com.todo.controller;

import com.todo.dto.TaskDTO;
import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/addTask")
public class AddTaskServlet extends HttpServlet{
	private TaskService service = new TaskServiceImpl();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {
	    
	    String title = req.getParameter("title");
	    String description = req.getParameter("description");
	    String dueDateStr = req.getParameter("dueDate");

	    System.out.println("title: " + title);
	    System.out.println("description: " + description);
	    System.out.println("dueDate: " + dueDateStr);

	    Date dueDate = null;
	    if (dueDateStr != null && !dueDateStr.isEmpty()) {
	        try {
	            dueDate = Date.valueOf(dueDateStr);
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	            resp.getWriter().print("Invalid date format.");
	            return;
	        }
	    }

	    TaskDTO task = new TaskDTO(0, title, description, dueDate, false);

	    try {
	        boolean isSaved = service.addTask(task);

	        if (isSaved) {
	        	resp.sendRedirect("listTasks");

	        } else {
	            resp.getWriter().print("Failed to add task - Service returned false");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        resp.getWriter().print("Failed to add task due to exception: " + e.getMessage());
	    }
	}

}