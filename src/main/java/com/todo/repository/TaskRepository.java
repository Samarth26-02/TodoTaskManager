package com.todo.repository;

import com.todo.dto.TaskDTO;
import com.todo.repository.DBConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
	private final String URL = "jdbc:mysql://localhost:3306/todo_app";
	private final String USER = "root";
	private final String PASSWORD = "0S1a2m3a4r5t6h@";

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	// Inserting a new Task
	public TaskDTO addTask(TaskDTO task) throws SQLException {
        String sql = "INSERT INTO tasks (title, description, due_date, is_completed) VALUES (?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setDate(3, task.getDueDate());
            ps.setBoolean(4, task.isCompleted());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting task failed, no rows affected.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    task.setId(generatedId);
                }
            }
            return task;
        }
    }

	// Retrieve Tasks
	public List<TaskDTO> getAllTasks() {
		List<TaskDTO> taskList = new ArrayList<>();
		String query = "SELECT * FROM tasks ORDER BY due_date ASC";

		try (Connection con = DBConnectionPool.getDataSource().getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				TaskDTO task = new TaskDTO(
						rs.getInt("id"),
						rs.getString("title"),
						rs.getString("description"),
						rs.getDate("due_date"),
						rs.getBoolean("is_completed"));
				taskList.add(task);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return taskList;
	}

	// Deleting Task
	public boolean deleteTask(int taskId) {
		String query = "DELETE FROM tasks WHERE id=?";

		try (Connection con = DBConnectionPool.getDataSource().getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, taskId);
			return stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// Mark Task as Completed
	public boolean markAsCompleted(int taskId) {
		String query = "UPDATE tasks SET is_completed = NOT is_completed WHERE id=?";

		try (Connection con = DBConnectionPool.getDataSource().getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, taskId);
			return stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public TaskDTO getTaskById(int id) {
		String query = "SELECT * FROM tasks WHERE id=?";
		try (Connection con = DBConnectionPool.getDataSource().getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new TaskDTO(
							rs.getInt("id"),
							rs.getString("title"),
							rs.getString("description"),
							rs.getDate("due_date"),
							rs.getBoolean("is_completed"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//Update task
	public boolean updateTask(TaskDTO task) {
		String query = "UPDATE tasks SET title=?, description=?, due_date=?, is_completed=? WHERE id=?";
		try (Connection con = DBConnectionPool.getDataSource().getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
			stmt.setString(1, task.getTitle());
			stmt.setString(2, task.getDescription());
			stmt.setDate(3, task.getDueDate());
			stmt.setBoolean(4, task.isCompleted());
			stmt.setInt(5, task.getId());

			return stmt.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}