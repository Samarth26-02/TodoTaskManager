package com.todo.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;
import com.todo.repository.DBConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet(urlPatterns = {"/deleteTask", "/api/tasks/delete/*"})
public class DeleteTaskServlet extends HttpServlet {
    private final TaskService taskService = new TaskServiceImpl();
    private final Gson gson = new Gson();

    private void setCORS(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000"); // Next.js
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

  
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setCORS(resp);

        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendRedirect("listTasks?error=Missing+id");
            return;
        }

        int taskId = Integer.parseInt(idStr);
        boolean deleted = taskService.deleteTask(taskId);

        resetAutoIncrementIfEmpty();

        resp.sendRedirect("listTasks" + (deleted ? "?success=Task+Deleted" : "?error=Unable+to+delete"));
    }

  
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setCORS(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, errorObj("Missing id"));
            return;
        }

        int taskId;
        try {
            taskId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, errorObj("Invalid id"));
            return;
        }

        boolean deleted = taskService.deleteTask(taskId);

        if (deleted) {
            resetAutoIncrementIfEmpty();
            writeJson(resp, HttpServletResponse.SC_OK, successObj("deleted", true));
        } else {
            writeJson(resp, HttpServletResponse.SC_NOT_FOUND, errorObj("Task not found"));
        }
    }

    
    private void resetAutoIncrementIfEmpty() {
        try (Connection conn = DBConnectionPool.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM tasks")) {

            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("ALTER TABLE tasks AUTO_INCREMENT = 1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
