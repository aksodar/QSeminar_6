package ru.sberbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    TaskService taskService;

    final int TASK_ID_A = 1234;
    final String TASK_SUMMARY_A = "Stub-task A";
    final int TASK_ID_B = 3421;
    final String TASK_SUMMARY_B = "Stub-task B";
    final int FAIL_ID = 9182;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
        Task task = new Task(TASK_ID_A, TASK_SUMMARY_A);
    }

    @Test
    void getTasks_TestSuccess() {
        List<Task> expected = List.of(
                new Task(TASK_ID_A, TASK_SUMMARY_A),
                new Task(TASK_ID_B, TASK_SUMMARY_B));
        taskService.createTask(TASK_ID_A, TASK_SUMMARY_A);
        taskService.createTask(TASK_ID_B, TASK_SUMMARY_B);

        List<Task> actual = taskService.getTasks();
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getSummary(), actual.get(i).getSummary());
        }
    }

    @Test
    void setTasks_TestSuccess() {
        ArrayList<Task> expected = new ArrayList<>(List.of(
                new Task(TASK_ID_A, TASK_SUMMARY_A),
                new Task(TASK_ID_B, TASK_SUMMARY_B)));
        taskService.setTasks(expected);
        assertEquals(expected, taskService.getTasks());
    }

    @Test
    void createTask_TestFail() {
        String expected = "Входные данные не валидны";
        String actual = assertThrows(IllegalStateException.class,
                () -> taskService.createTask(TASK_ID_A, null)).getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void createTask_TestSuccess() {
        taskService.createTask(TASK_ID_A, TASK_SUMMARY_A);
        assertEquals(TASK_ID_A, taskService.getTask(TASK_ID_A).getId());
        assertEquals(TASK_SUMMARY_A, taskService.getTask(TASK_ID_A).getSummary());
    }

    @Test
    void getTask_TestFail() {
        String expected = "Задача не найдена";
        String actual = assertThrows(IllegalStateException.class,
                () -> taskService.getTask(TASK_ID_A)).getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void getTask_TestSuccess() {
        taskService.createTask(TASK_ID_A, TASK_SUMMARY_A);
        taskService.createTask(TASK_ID_B, TASK_SUMMARY_B);

        Task actual = taskService.getTask(TASK_ID_A);
        assertEquals(TASK_ID_A, actual.getId());
        assertEquals(TASK_SUMMARY_A, actual.getSummary());
    }

    @Test
    void testGetTask_TestFail() {
        String expected = "Задача не найдена";
        taskService.createTask(TASK_ID_A, TASK_SUMMARY_A);
        taskService.createTask(TASK_ID_B, TASK_SUMMARY_B);

        String actual = assertThrows(IllegalStateException.class,
                () -> taskService.getTask(FAIL_ID)).
                getMessage();
        assertEquals(expected, actual);
    }

    @Test
    void testGetTask_TestSuccess() {
        ArrayList<Task> tasksList = new ArrayList<>(List.of(
                new Task(TASK_ID_A, TASK_SUMMARY_A),
                new Task(TASK_ID_B, TASK_SUMMARY_B)));
        taskService.setTasks(tasksList);
        Task expected = tasksList.get(0);
        assertEquals(expected, taskService.getTask(expected.getId()));
        assertEquals(expected, taskService.getTask(expected.getSummary()));
    }

    @Test
    void getTasksForDeveloping_TestSuccess() {
        ArrayList<Task> tasksList = new ArrayList<>(List.of(
                new Task(TASK_ID_A, TASK_SUMMARY_A),
                new Task(TASK_ID_B, TASK_SUMMARY_B),
                new Task(TASK_ID_B, TASK_SUMMARY_A),
                new Task(TASK_ID_A, TASK_SUMMARY_B))
        );
        tasksList.get(0).setDeveloped(true);
        tasksList.get(1).setDeveloped(true);
        List<Task> expected = new ArrayList<>(tasksList);
        expected.remove(0);
        expected.remove(0);
        taskService.setTasks(tasksList);

        assertEquals(expected, taskService.getTasksForDeveloping());

    }
}