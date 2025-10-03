package com.todo.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.todo.dto.TaskDTO;
import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/tasks/*")
public class TasksApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();
    private final TaskService service = new TaskServiceImpl();

    private void setCORS(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); // Next.js frontend
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    private JsonObject parseBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return gson.fromJson(sb.toString(), JsonObject.class);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORS(resp); 
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo(); 
        if (pathInfo == null || "/".equals(pathInfo)) {
            List<TaskDTO> tasks = service.getAllTasks();
            resp.getWriter().write(gson.toJson(tasks));
            return;
        }

        String idStr = pathInfo.substring(1); 
        try {
            int id = Integer.parseInt(idStr);
            TaskDTO task = service.getTaskById(id);
            if (task == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\":\"Task not found\"}");
                return;
            }
            resp.getWriter().write(gson.toJson(task));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Invalid id\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORS(resp); 
        resp.setContentType("application/json");

        JsonObject body = parseBody(req);
        String title = body.has("title") ? body.get("title").getAsString() : "";
        String description = body.has("description") ? body.get("description").getAsString() : "";
        String dueDateStr = body.has("dueDate") ? body.get("dueDate").getAsString() : null;

        java.sql.Date dueDate = null;
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            try {
                dueDate = java.sql.Date.valueOf(dueDateStr);
            } catch (IllegalArgumentException ignore) {}
        }

        TaskDTO task = new TaskDTO(0, title, description, dueDate, false);

        try {
            TaskDTO savedTask = service.addTask(task);
            if (savedTask != null && savedTask.getId() > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write(gson.toJson(savedTask));
            } else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("{\"error\":\"Unable to save task\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Something went wrong: " + e.getMessage() + "\"}");
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORS(resp); 
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing id\"}");
            return;
        }
        int id = Integer.parseInt(pathInfo.substring(1));
        JsonObject body = parseBody(req);

        String title = body.has("title") ? body.get("title").getAsString() : null;
        String description = body.has("description") ? body.get("description").getAsString() : null;
        String dueDateStr = body.has("dueDate") ? body.get("dueDate").getAsString() : null;
        Boolean isCompleted = body.has("isCompleted") ? body.get("isCompleted").getAsBoolean() : null;

        java.sql.Date dueDate = null;
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            try {
                dueDate = java.sql.Date.valueOf(dueDateStr);
            } catch (IllegalArgumentException ignore) {}
        }

        TaskDTO existing = service.getTaskById(id);
        if (existing == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Task not found\"}");
            return;
        }

        if (title != null) existing.setTitle(title);
        if (description != null) existing.setDescription(description);
        if (dueDate != null) existing.setDueDate(dueDate);
        if (isCompleted != null) existing.setIsCompleted(isCompleted);

        boolean ok = service.updateTask(existing);
        if (ok) {
            resp.getWriter().write(gson.toJson(existing));
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Unable to update\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORS(resp); 
        resp.setContentType("application/json");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing id\"}");
            return;
        }
        int id = Integer.parseInt(pathInfo.substring(1));
        boolean removed = service.deleteTask(id);
        if (removed) {
            resp.getWriter().write("{\"ok\":true}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Task not found\"}");
        }
    }
}
