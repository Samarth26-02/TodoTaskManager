CREATE DATABASE todo_app;

USE todo_app;

CREATE TABLE tasks(
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description TEXT,
due_date DATE,
is_completed BOOLEAN DEFAULT FALSE
);

select *from tasks;