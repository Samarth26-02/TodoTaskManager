
package com.todo.controller;

import com.todo.service.TaskService;
import com.todo.service.TaskServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/deleteTask")
public class DeleteTaskServlet extends HttpServlet {
    private final TaskService taskService = new TaskServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int taskId = Integer.parseInt(req.getParameter("id"));
        taskService.deleteTask(taskId);

        //Reset Id logic
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_app", "root", "0S1a2m3a4r5t6h@");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM tasks")) {

            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("ALTER TABLE tasks AUTO_INCREMENT = 1");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("listTasks");
    }
}
