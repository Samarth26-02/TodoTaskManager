package com.todo.controller;

import com.todo.dto.TaskDTO;
import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/api/updateTask")
public class UpdateTaskServlet extends HttpServlet {
    private TaskService taskService = new TaskServiceImpl();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"error\":\"Task ID is required\"}");
                return;
            }

            int taskId = Integer.parseInt(idParam);
            TaskDTO task = taskService.getTaskById(taskId);

            if (task == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"error\":\"Task not found\"}");
                return;
            }

            req.setAttribute("task", task);

            req.getRequestDispatcher("/WEB-INF/views/editTask.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Invalid task ID format\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = req.getReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            TaskDTO updatedTask = gson.fromJson(sb.toString(), TaskDTO.class);

            if (updatedTask.getTitle() == null || updatedTask.getTitle().trim().isEmpty()
                    || updatedTask.getDescription() == null || updatedTask.getDescription().trim().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"error\":\"Title and Description cannot be empty\"}");
                return;
            }

            if (updatedTask.getDueDate() != null) {
                updatedTask.setDueDate(Date.valueOf(updatedTask.getDueDate().toString()));
            }

            boolean success = taskService.updateTask(updatedTask);

            resp.setContentType("application/json");
            if (success) {
                resp.getWriter().write("{\"message\":\"Task updated successfully\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"Failed to update task\"}");
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Invalid request format\"}");
        }
    }
}
