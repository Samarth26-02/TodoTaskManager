# ✅ Todo Task Manager - Built in Java Full Stack

A full-stack Todo Task Manager web application built using Java Servlets, JSP, JDBC, MySQL, HTML, CSS, JavaScript, and Bootstrap.  
This application allows users to efficiently manage their daily tasks with basic CRUD (Create, Read, Update, Delete) functionality.

---

## ✨ Features

- ✅ Create new tasks with title and description  
- ✅ View all tasks in a clean list  
- ✅ Update existing tasks  
- ✅ Delete tasks  
- ✅ Mark tasks as completed  
- ✅ Tasks stored in MySQL database  
- ✅ Responsive and user-friendly UI using Bootstrap  

---

## 🛠 Tech Stack

- **Frontend:** HTML, CSS, JavaScript, JSP, Bootstrap  
- **Backend:** Java Servlets, JDBC  
- **Database:** MySQL  
- **IDE & Server:** IntelliJ IDEA / Eclipse, Apache Tomcat  

---
📂 Project Structure
```
TodoTaskManager/
 ├── src/  
 │   └── com.todo/ (Servlets, DTOs, Services, Repository)  
 ├── WebContent/  
 │   ├── index.jsp  
 │   ├── tasks.jsp  
 │   ├── updatetask.jsp  
 │   └── lib/ (JDBC Driver - mysql-connector)  
 └── .project / .classpath (for Eclipse setup)
```
---

## 🧰 Prerequisites

- Java (JDK 8+)
- Apache Tomcat
- MySQL
- Eclipse or IntelliJ IDEA 

---

## ⚙️ Setup Instructions

### 🔧 1. Database Setup

- Open MySQL Workbench or CLI
- Create a new database and table:

```sql
CREATE DATABASE todo_app;

USE todo_app;

CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    description TEXT,
    due_date DATE,
    status VARCHAR(20) DEFAULT 'PENDING'
);
```

### 🔧 2. Java Project Setup
Import the project into Eclipse or IntelliJ.

Place MySQL JDBC JAR into lib/ folder and add to build path.

Update DB credentials in DBConnection.java:

```
    String URL = "jdbc:mysql://localhost:3306/todo_app";
    String USER = "root";
    String PASSWORD = "0S1a2m3a4r5t6h@";
```
---
## ▶️ Run Setup

Follow these steps to run the project locally on your laptop:

1. Clone the Repository
git clone https://github.com/Samarth26-02/TodoTaskManager.git
cd TodoTaskManager

2. Add MySQL Connector JAR

Download MySQL Connector/J from:
[MySQL Connector/J Download](https://dev.mysql.com/downloads/connector/j/)

Place the .jar file inside the lib/ folder of the project.

Add it to your project build path (in Eclipse/IntelliJ).

3. Deploy to Apache Tomcat

Install Apache Tomcat from:https://tomcat.apache.org/download-90.cgi

Configure Tomcat in Eclipse/IntelliJ.

Deploy the project to Tomcat.

Start the Tomcat server.

4. Run the Application

Open your browser and navigate to:

http://localhost:8080/TodoTaskManager/

You should now see the Todo Task Manager app running 🎉
---

✅ Deliverables
📦 Working source code (.zip)

📘 README with setup instructions

⏳ Deadline
Submit by 31st July 2025

👨‍💻 Author
T S Samarth

