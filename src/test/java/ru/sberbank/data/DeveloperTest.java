package ru.sberbank.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperTest {

    Developer developer;
    Task task;

    @BeforeEach
    void setUp() {
        developer = new Developer(5678, "Smith", "Doe");
        task = new Task(8765, "Developer-stub-task");
    }

    @Test
    void makeTaskTest_Fail() {
        String expected = "Нет задания для исполнения!";
        Exception exception = assertThrows(IllegalStateException.class,
                () -> developer.makeTask());
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void makeTaskTest_Success() {
        developer.addTask(task);
        assertEquals(task, developer.makeTask());
    }

}