package com.todo.controller;

import com.google.gson.Gson;
import com.todo.dto.TaskDTO;
import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/listTasks", "/api/tasks/list"})
public class TaskListServlet extends HttpServlet {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setCORS(resp);

        String servletPath = req.getServletPath();

        if ("/api/tasks/list".equals(servletPath)) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            List<TaskDTO> tasks = service.getAllTasks();
            String json = gson.toJson(tasks);

            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
            }
        } else {
            List<TaskDTO> tasks = service.getAllTasks();
            req.setAttribute("tasks", tasks);
            req.getRequestDispatcher("tasks.jsp").forward(req, resp);
        }
    }
}
