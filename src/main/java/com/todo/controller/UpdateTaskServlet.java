package com.todo.controller;

import com.todo.dto.TaskDTO;
import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/updateTask")
public class UpdateTaskServlet extends HttpServlet {
    private TaskService taskService = new TaskServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int taskId = Integer.parseInt(req.getParameter("id"));
        TaskDTO task = taskService.getTaskById(taskId);
        req.setAttribute("task", task);
        req.getRequestDispatcher("updatetasks.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String dueDateStr = req.getParameter("dueDate");
        boolean isCompleted = "on".equals(req.getParameter("isCompleted"));

        Date dueDate = null;
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            dueDate = Date.valueOf(dueDateStr);
        }

        TaskDTO updatedTask = new TaskDTO(id, title, description, dueDate, isCompleted);

        boolean success = taskService.updateTask(updatedTask);
        if (success) {
            resp.sendRedirect("listTasks");
        } else {
            resp.getWriter().print("Failed to update task.");
        }
    }
}
