package com.todo.dto;

import java.sql.Date;

public class TaskDTO{
	private int id;
	private String title;
	private String description;
	private Date dueDate;
	private boolean isCompleted;
	
	public TaskDTO() {}
	
	public TaskDTO(int id,String title,String description,Date dueDate,boolean isCompleted) {
		this.id=id;
		this.title=title;
		this.description=description;
		this.dueDate=dueDate;
		this.isCompleted=isCompleted;
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public boolean isCompleted() {
		return isCompleted;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public void setTitle(String title) {
		this.title=title;
	}
	
	public void setDescription(String description) {
		this.description=description;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate=dueDate;
	}
	
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted=isCompleted;
	}
	
	@Override
	public String toString() {
		return "TaskDTO [Id="+id+",Title="+title+",Description="+description+",Due Date="+dueDate+",Completed="+isCompleted+"]";
	}
}