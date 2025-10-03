package com.todo.repository;

import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class DBConnectionPool{
	public static final BasicDataSource dataSource=new BasicDataSource();
	static {
		dataSource.setUrl("jdbc:mysql://localhost:3306/todo_app");
		dataSource.setUsername("root");
		dataSource.setPassword("0S1a2m3a4r5t6h@");
		dataSource.setMinIdle(5);
		dataSource.setMaxIdle(10);
		dataSource.setMaxOpenPreparedStatements(100);
	}
	
	
	public static DataSource getDataSource() {
		return dataSource;
	}
}