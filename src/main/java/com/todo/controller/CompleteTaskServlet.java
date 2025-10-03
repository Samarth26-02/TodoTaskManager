package com.todo.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = {"/completeTask", "/api/tasks/complete"})
public class CompleteTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final TaskService taskService = new TaskServiceImpl();
    private final Gson gson = new Gson();

    private void setCORS(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORS(resp);

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            resp.sendRedirect("listTasks?error=Missing+id");
            return;
        }

        int taskId;
        try {
            taskId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect("listTasks?error=Invalid+id");
            return;
        }

        boolean ok = taskService.markTaskAsCompleted(taskId);
        resp.sendRedirect("listTasks" + (ok ? "?success=Task+marked+as+completed." : "?error=Unable+to+mark+task"));
    }

    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORS(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo(); 
        if (pathInfo == null || pathInfo.length() <= 1) {
            writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, errorObj("Missing id"));
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length < 2) {
            writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, errorObj("Invalid path"));
            return;
        }

        int id;
        try {
            id = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, errorObj("Invalid id"));
            return;
        }

        boolean ok = taskService.markTaskAsCompleted(id);
        if (ok) {
            writeJson(resp, HttpServletResponse.SC_OK, successObj("ok", true));
        } else {
            writeJson(resp, HttpServletResponse.SC_NOT_FOUND, errorObj("Task not found or could not update"));
        }
    }

    private void writeJson(HttpServletResponse resp, int status, JsonObject obj) throws IOException {
        resp.setStatus(status);
        try (PrintWriter out = resp.getWriter()) {
            out.write(gson.toJson(obj));
        }
    }

    private JsonObject errorObj(String msg) {
        JsonObject o = new JsonObject();
        o.addProperty("error", msg);
        return o;
    }

    private JsonObject successObj(String key, boolean value) {
        JsonObject o = new JsonObject();
        o.addProperty(key, value);
        return o;
    }
}
