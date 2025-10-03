"use client";
import { useEffect, useState } from "react";
import { useRouter, useParams } from "next/navigation";
import styles from "../../addtasks/Form.module.css";
import { API_ENDPOINTS } from "../../config/api"; 

export default function UpdateTaskPage() {
  const { id } = useParams();
  const router = useRouter();
  const [task, setTask] = useState({ title: "", description: "", dueDate: "" });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    async function fetchTask() {
      try {
        const res = await fetch(`${API_ENDPOINTS.TASKS}/${id}`);
        if (!res.ok) throw new Error("Failed to load task data.");
        const data = await res.json();

        let formattedDate = "";
        if (data.dueDate) {
          const d = new Date(data.dueDate);
          formattedDate = `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, "0")}-${String(d.getDate()).padStart(2, "0")}`;
        }
        setTask({ ...data, dueDate: formattedDate });
      } catch (err) {
        alert(err.message);
        router.push("/tasks");
      }
    }
    if (id) fetchTask();
  }, [id, router]);

  const save = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const res = await fetch(`${API_ENDPOINTS.UPDATE_TASK}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(task),
      });
      if (!res.ok) throw new Error("Failed to update task");
      router.push("/tasks");
    } catch (err) {
      alert(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTask(prev => ({ ...prev, [name]: value }));
  };

  return (
    <div className={styles.container}>
      <h1 className={styles.heading}>Edit Task</h1>
      <form onSubmit={save}>
        <div className={styles.formGroup}>
          <label>Title</label>
          <input name="title" type="text" value={task.title} onChange={handleChange} required />
        </div>
        <div className={styles.formGroup}>
          <label>Description</label>
          <textarea name="description" value={task.description} onChange={handleChange} required />
        </div>
        <div className={styles.formGroup}>
          <label>Due Date</label>
          <input name="dueDate" type="date" value={task.dueDate} onChange={handleChange} />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? "Saving..." : "Save Changes"}
        </button>
      </form>
    </div>
  );
}
