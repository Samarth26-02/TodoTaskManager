<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Todo Task Manager</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center mb-4">Add New Task</h2>
         <%
            String successMsg = request.getParameter("success");
            if (successMsg != null) {
        %>
            <div class="alert alert-success text-center">
                <%= successMsg %>
            </div>
        <%
            }
        %>

        <%
            String errorMsg = (String) request.getAttribute("error");
            if (errorMsg != null) {
        %>
            <div class="alert alert-danger text-center">
                <%= errorMsg %>
            </div>
        <%
            }
        %>
        <form action="addTask" method="post" class="shadow p-4 rounded bg-light">

            <div class="mb-3">
                <label for="title" class="form-label">Title</label>
                <input type="text" class="form-control" id="title" name="title" required>
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
            </div>

            <div class="mb-3">
                <label for="dueDate" class="form-label">Date</label>
                <input type="date" class="form-control" id="dueDate" name="dueDate" required>
            </div>

            <button type="submit" class="btn btn-primary w-100">Add Task</button>
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
