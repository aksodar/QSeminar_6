package ru.sberbank.service;

import ru.sberbank.data.Task;

import java.util.ArrayList;

public class TaskService {
    private ArrayList<Task> tasks;

    public TaskService() {
        this.tasks = new ArrayList<>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task createTask(int id, String summary) throws IllegalStateException {
        if (summary == null || summary.isEmpty()) {
            throw new IllegalStateException("Входные данные не валидны");
        }
        Task task = new Task(id, summary);
        tasks.add(task);
        return task;
    }

    public Task getTask(String summary) throws IllegalStateException {
        for (Task task : tasks) {
            if (summary.equalsIgnoreCase(task.getSummary())) {
                return task;
            }
        }
        throw new IllegalStateException("Задача не найдена");
    }

    public Task getTask(int id) throws IllegalStateException {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        throw new IllegalStateException("Задача не найдена");
    }

    public ArrayList<Task> getTasksForDeveloping() {
        ArrayList<Task> list = new ArrayList<>();
        for (Task n : tasks) {
            if (!n.isDeveloped()) {
                list.add(n);
            }
        }
        return list;
    }
}
