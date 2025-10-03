import "./globals.css";
import Navbar from "./components/Navbar";

export const metadata = {
  title: "Todo App",
  description: "Task Manager built with Next.js + Servlets backend",
};

export default function RootLayout({ children }) {
  return (
    <html lang="en">
      <body>
        <Navbar />
        <main>{children}</main>
      </body>
    </html>
  );
}
