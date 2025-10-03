<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.todo.dto.TaskDTO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Task List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4 text-center">Your Tasks</h2>

    <%
        List<TaskDTO> tasks = (List<TaskDTO>) request.getAttribute("tasks");
        if (tasks != null && !tasks.isEmpty()) {
    %>
    <table class="table table-bordered table-hover shadow">
        <thead class="table-light">
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Description</th>
            <th>Date</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (TaskDTO task : tasks) {
        %>
        <tr>
            <td><%= task.getId() %></td>
            <td><%= task.getTitle() %></td>
            <td><%= task.getDescription() %></td>
            <td><%= task.getDueDate() %></td>
            <td>
                <span class="badge <%= task.isCompleted() ? "bg-success" : "bg-warning text-dark" %>">
                    <%= task.isCompleted() ? "Completed" : "Pending" %>
                </span>
            </td>
            <td>
                <div class="d-flex gap-2 flex-wrap">
                    <!-- Delete Button -->
                    <form action="deleteTask" method="post">
                        <input type="hidden" name="id" value="<%= task.getId() %>"/>
                        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                    </form>

                    <!-- Complete Button -->
                    <%
                        if (!task.isCompleted()) {
                    %>
                    <form action="completeTask" method="post">
                        <input type="hidden" name="id" value="<%= task.getId() %>"/>
                        <button type="submit" class="btn btn-success btn-sm">Mark Complete</button>
                    </form>
                    <%
                        }
                    %>

                    <!-- Edit Button -->
                    <a href="updateTask?id=<%= task.getId() %>" class="btn btn-warning btn-sm">Edit</a>
                </div>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
        } else {
    %>
    <div class="alert alert-info text-center" role="alert">
        No tasks found. <a href="addtasks.jsp" class="alert-link">Add a task</a>
    </div>
    <%
        }
    %>

    <div class="text-center mt-4">
        <a href="addtasks.jsp" class="btn btn-primary">Add New Task</a>
    </div>
    
    <div class="text-center mt-4">
        <a href="index.jsp" class="btn btn-secondary">Back to Home</a>
    </div>
    
</div>
</body>
</html>
