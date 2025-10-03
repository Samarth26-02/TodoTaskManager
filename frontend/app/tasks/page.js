"use client";
import { useEffect, useState } from "react";
import Link from "next/link";
import styles from "./Tasks.module.css";
import { API_ENDPOINTS } from "../config/api";

export default function TasksPage() {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState(null);
  useEffect(() => {
    async function fetchTasks() {
      try {
        const res = await fetch(API_ENDPOINTS.TASKS);
        if (!res.ok) throw new Error("Failed to fetch tasks");
        setTasks(await res.json());
      } catch (err) {
        setError(err.message);
      }
    }
    fetchTasks();
  }, []);

  const deleteTask = async (id) => {
    try {
      const res = await fetch(`${API_ENDPOINTS.DELETE_TASK}/${id}`, { method: "DELETE" });
      if (!res.ok) throw new Error("Failed to delete task");
      setTasks(tasks.filter((t) => t.id !== id));
    } catch (err) {
      alert(err.message);
    }
  };

  const toggleComplete = async (taskToUpdate) => {
    try {
      const payload = { ...taskToUpdate, isCompleted: !taskToUpdate.isCompleted };
      const res = await fetch(`${API_ENDPOINTS.UPDATE_TASK}/${taskToUpdate.id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      if (!res.ok) throw new Error("Failed to update status");
      setTasks(tasks.map(t => t.id === taskToUpdate.id ? { ...t, isCompleted: !t.isCompleted } : t));
    } catch (err) {
      alert(err.message);
    }
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.heading}>Your Tasks</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {tasks.length === 0 && !error ? (
        <p>No tasks found.</p>
      ) : (
        <ul className={styles.taskList}>
          {tasks.map((task) => (
            <li key={task.id} className={task.isCompleted ? styles.taskCompleted : styles.task}>
              <div>
                <h2>{task.title}</h2>
                <p>{task.description}</p>
                <p className={styles.dueDate}>Due: {task.dueDate ? new Date(task.dueDate).toLocaleDateString() : "N/A"}</p>
              </div>
              <div className={styles.buttons}>
                <button onClick={() => toggleComplete(task)} className={styles.complete}>
                  {task.isCompleted ? "Undo" : "Complete"}
                </button>
                <Link href={`/updatetasks/${task.id}`} className={styles.edit}>Edit</Link>
                <button onClick={() => deleteTask(task.id)} className={styles.delete}>Delete</button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
