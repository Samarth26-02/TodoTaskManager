import Link from "next/link";
import styles from "./Home.module.css";

export default function Home() {
  return (
    <main className={styles.container}>
      <h1 className={styles.heading}>Welcome to Todo Task Manager</h1>
      <div className={styles.buttons}>
        <Link href="/addtasks" className={styles.buttonPrimary}>Add Task</Link>
        <Link href="/tasks" className={styles.buttonSecondary}>View Tasks</Link>
      </div>
    </main>
  );
}
