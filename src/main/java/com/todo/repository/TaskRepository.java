package com.todo.repository;

import com.todo.dto.TaskDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository{
	private final String URL="jdbc:mysql://localhost:3306/todo_app";
	private final String USER="root";
	private final String PASSWORD="0S1a2m3a4r5t6h@";
	
	private Connection getConnection() throws SQLException{
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	//Inserting a new Task
	public boolean addTask(TaskDTO task) {
		String query="INSERT INTO tasks (title,description,due_date,is_completed) VALUES(?,?,?,?)";
	
		try(Connection con=DBConnection.getConnection(); PreparedStatement stmt=con.prepareStatement(query)){
			stmt.setString(1, task.getTitle());
			stmt.setString(2, task.getDescription());
			stmt.setDate(3, task.getDueDate());
			stmt.setBoolean(4, task.isCompleted());
			
			int rowsInserted=stmt.executeUpdate();
			System.out.println("Rows inserted:"+rowsInserted);
			return rowsInserted >0;
		}catch(SQLException e) {
		    System.out.println("Error while inserting task:");
			e.printStackTrace();
		}
		
		return false;
	}
	
	//Retrieve Tasks
	public List<TaskDTO> getAllTasks(){
		List<TaskDTO> taskList=new ArrayList<>();
		String query="SELECT * FROM tasks";
		
		try(Connection con=getConnection(); Statement stmt=con.createStatement(); ResultSet rs=stmt.executeQuery(query)){
			while(rs.next()) {
				TaskDTO task=new TaskDTO(
						rs.getInt("id"),
						rs.getString("title"),
						rs.getString("description"),
						rs.getDate("due_date"),
						rs.getBoolean("is_completed")
						);
				taskList.add(task);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return taskList;
	}
	
	//Deleting Task 
	public boolean deleteTask(int taskId) {
		String query="DELETE FROM tasks WHERE id=?";
		
		try(Connection con=getConnection(); PreparedStatement stmt=con.prepareStatement(query)){
			stmt.setInt(1, taskId);
			return stmt.executeUpdate() == 1;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//Mark Task as Completed
	public boolean markAsCompleted(int taskId) {
		String query="UPDATE tasks SET is_completed = true WHERE id=?";
		
		try(Connection con=getConnection(); PreparedStatement stmt=con.prepareStatement(query)){
			stmt.setInt(1,taskId);
			return stmt.executeUpdate() == 1;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public TaskDTO getTaskById(int id) {
	    String query = "SELECT * FROM tasks WHERE id=?";
	    try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
	        stmt.setInt(1, id);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return new TaskDTO(
	                    rs.getInt("id"),
	                    rs.getString("title"),
	                    rs.getString("description"),
	                    rs.getDate("due_date"),
	                    rs.getBoolean("is_completed")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	public boolean updateTask(TaskDTO task) {
	    String query = "UPDATE tasks SET title=?, description=?, due_date=?, is_completed=? WHERE id=?";
	    try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {
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