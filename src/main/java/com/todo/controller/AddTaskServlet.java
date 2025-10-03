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
import java.io.PrintWriter;
import java.sql.Date;

@WebServlet(urlPatterns = {"/addTask", "/api/tasks/add"})
public class AddTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final TaskService service = new TaskServiceImpl();
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

    private JsonObject parseJsonBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        try {
            return gson.fromJson(sb.toString(), JsonObject.class);
        } catch (Exception e) {
            return null;
        }
    }

    private void writeJson(HttpServletResponse resp, int status, Object body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.write(gson.toJson(body));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setCORS(resp);

        String contentType = req.getContentType() == null ? "" : req.getContentType().toLowerCase();

        String title = null;
        String description = null;
        String dueDateStr = null;

        if (contentType.contains("application/json")) {
            JsonObject json = parseJsonBody(req);
            if (json == null) {
                writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, new JsonObjectBuilder("error", "Invalid JSON").build());
                return;
            }
            if (json.has("title") && !json.get("title").isJsonNull()) title = json.get("title").getAsString();
            if (json.has("description") && !json.get("description").isJsonNull()) description = json.get("description").getAsString();
            if (json.has("dueDate") && !json.get("dueDate").isJsonNull()) dueDateStr = json.get("dueDate").getAsString();
        } else {
            title = req.getParameter("title");
            description = req.getParameter("description");
            dueDateStr = req.getParameter("dueDate");
        }

        if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty()) {
            writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, new JsonObjectBuilder("error", "Title and Description cannot be empty").build());
            return;
        }

        Date dueDate = null;
        if (dueDateStr != null && !dueDateStr.isEmpty()) {
            try {
                dueDate = Date.valueOf(dueDateStr);
            } catch (IllegalArgumentException e) {
                writeJson(resp, HttpServletResponse.SC_BAD_REQUEST, new JsonObjectBuilder("error", "Invalid dueDate format. Use yyyy-MM-dd").build());
                return;
            }
        }

        TaskDTO task = new TaskDTO(0, title, description, dueDate, false);

        try {
            TaskDTO savedTask = service.addTask(task);
            if (savedTask != null && savedTask.getId() > 0) {
                writeJson(resp, HttpServletResponse.SC_CREATED, savedTask);
            } else {
                writeJson(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    new JsonObjectBuilder("error", "Unable to add task").build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            writeJson(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                new JsonObjectBuilder("error", "Something went wrong: " + e.getMessage()).build());
        }
    }

    private static class JsonObjectBuilder {
        private final JsonObject obj = new JsonObject();
        JsonObjectBuilder(String key, String value) { obj.addProperty(key, value); }
        JsonObjectBuilder(String key, boolean value) { obj.addProperty(key, value); }
        JsonObject build() { return obj; }
    }
}

