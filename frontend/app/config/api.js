// API configuration for connecting to backend
// Using relative paths since Next.js rewrites proxy /api/* to backend at localhost:8080/TodoTaskManager/api/*
export const API_ENDPOINTS = {
  TASKS: '/api/tasks',
  ADD_TASK: '/api/tasks/add',
  UPDATE_TASK: '/api/tasks',
  DELETE_TASK: '/api/tasks'
};
