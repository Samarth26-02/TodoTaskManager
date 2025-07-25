package com.todo.controller;

import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/completeTask")
public class CompleteTaskServlet extends HttpServlet {
    private final TaskService taskService = new TaskServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int taskId = Integer.parseInt(req.getParameter("id"));
        taskService.markTaskAsCompleted(taskId);
        resp.sendRedirect("listTasks");
    }
}
