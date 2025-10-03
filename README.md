# ✅ Todo Task Manager - Built in Java Full Stack

A full-stack Todo Task Manager web application built using Java Servlets, Next.js, JDBC (MySQL), HTML, CSS, JavaScript, and Bootstrap. This application allows users to efficiently manage their daily tasks with basic CRUD (Create, Read, Update, Delete) functionality.

## ✨ Features
- ✅ Create new tasks with title and description
- ✅ View all tasks in a clean list
- ✅ Update existing tasks
- ✅ Delete tasks
- ✅ Mark tasks as completed
- ✅ Tasks stored in MySQL database
- ✅ Responsive and user-friendly UI using Bootstrap

## 🛠 Tech Stack
- **Frontend:** Next.js, HTML, CSS, JavaScript, Bootstrap
- **Backend:** Java Servlets, JDBC
- **Database:** MySQL
- **IDE & Server:** IntelliJ IDEA / Eclipse, Apache Tomcat

## 🗂 Project Structure
```
TodoTaskManager/
├── src/
│   └── com.todo/ (Servlets, DTOs, Services, Repository)
├── frontend/ (Next.js application)
│   ├── pages/ (Next.js pages)
│   ├── components/ (React components)
│   ├── styles/ (CSS styles)
│   └── public/ (Static files)
├── lib/ (JDBC Driver - mysql-connector)
└── .project / .classpath (for Eclipse setup)
```
Copy

## 🧰 Prerequisites
- Java (JDK 8+)
- Apache Tomcat
- MySQL
- Node.js (for Next.js)
- npm or yarn (for package management)
- Eclipse or IntelliJ IDEA

## ⚙️ Setup Instructions

### 🔧 1. Database Setup
1. Open MySQL Workbench.
2. Create a new database and table:
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
🔧 2. Java Project Setup
Import the Java project into Eclipse or IntelliJ.
Place MySQL JDBC JAR into the lib/ folder and add it to the build path.
Update DB credentials in DBConnection.java:
Copy
String URL = "jdbc:mysql://localhost:3306/todo_app";
String USER = "root";
String PASSWORD = "0S1a2m3a4r5t6h@";
🔧 3. Frontend Setup
Navigate to the frontend/ directory.
Install dependencies:
Copy
npm install
or
Copy
yarn install
▶️ Run Setup
Follow these steps to run the project locally on your laptop:

Clone the Repository:

Copy
git clone https://github.com/Samarth26-02/TodoTaskManager.git
cd TodoTaskManager
Add MySQL Connector JAR:

Download MySQL Connector/J from: MySQL Connector/J Download.
Place the .jar file inside the lib/ folder of the project.
Add it to your project build path (in Eclipse/IntelliJ).
Deploy to Apache Tomcat:

Install Apache Tomcat.
Configure Tomcat in Eclipse/IntelliJ.
Deploy the project to Tomcat.
Start the Tomcat server.
Run the Next.js Application:

In the frontend/ directory, run:
Copy
npm run dev
or

Copy
yarn dev
Open your browser and navigate to:

Copy
http://localhost:3000
You should now see the Todo Task Manager app running 🎉

✅ Deliverables
📦 Working source code (.zip)
📘 README with setup instructions
⏳ Deadline
Submit by 31st July 2025

👨‍💻 Author
T S Samarth
