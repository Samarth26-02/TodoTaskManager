"use client";
import Link from "next/link";
import styles from "./Navbar.module.css";

export default function Navbar() {
  return (
    <nav className={styles.navbar}>
      <div className={styles.brand}>TodoApp</div>
      <div className={styles.links}>
        <Link href="/" className={styles.link}>Home</Link>
        <Link href="/tasks" className={styles.link}>Tasks</Link>
        <Link href="/addtasks" className={styles.link}>Add Task</Link>
      </div>
    </nav>
  );
}
