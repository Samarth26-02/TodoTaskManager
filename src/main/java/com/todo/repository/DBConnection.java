//package com.todo.repository;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection{
//	private static final String URL = "jdbc:mysql://localhost:3306/todo_app";
//	private static final String USER = "root";
//	private static final String PASSWORD = "0S1a2m3a4r5t6h@";
//	
//	public static Connection getConnection() throws SQLException{
//		return DriverManager.getConnection(URL, USER, PASSWORD);
//	}
//}