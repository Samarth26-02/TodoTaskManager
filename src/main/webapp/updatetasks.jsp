<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.todo.dto.TaskDTO" %>
<%
    TaskDTO task = (TaskDTO) request.getAttribute("task");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Task</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4 text-primary">Update Task</h2>
    <form action="updateTask" method="post" class="p-4 border rounded shadow bg-light">
        <input type="hidden" name="id" value="<%= task.getId() %>">

        <div class="mb-3">
            <label for="title" class="form-label">Title:</label>
            <input type="text" class="form-control" id="title" name="title" value="<%= task.getTitle() %>" required>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Description:</label>
            <textarea class="form-control" id="description" name="description" required><%= task.getDescription() %></textarea>
        </div>

        <div class="mb-3">
            <label for="dueDate" class="form-label">Due Date:</label>
            <input type="date" class="form-control" id="dueDate" name="dueDate" value="<%= task.getDueDate() %>" required>
        </div>

        <div class="form-check mb-3">
            <input type="checkbox" class="form-check-input" id="isCompleted" name="isCompleted" <%= task.isCompleted() ? "checked" : "" %>>
            <label class="form-check-label" for="isCompleted">Completed</label>
        </div>

        <button type="submit" class="btn btn-success">Update Task</button>
        <a href="listTasks" class="btn btn-secondary ms-2">Cancel</a>
    </form>
    
    <div class="text-center mt-4">
    <a href="index.jsp" class="btn btn-secondary">Back to Home</a>
</div>
    
</div>

<script>
    const today = new Date().toISOString().split("T")[0];
    document.getElementById("dueDate").setAttribute("min", today);
</script>
</body>
</html>
