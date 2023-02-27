package ru.sberbank.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    Task task;

    final int ID = 9876;
    final String SUMMARY = "Task-test task";

    @BeforeEach
    void setUp() {
        task = new Task(ID, SUMMARY);

    }

    @Test
    void getIdTest_Success() {
        assertEquals(ID, task.getId());
    }

    @Test
    void isDevelopedTest_Success() {
        assertFalse(task.isDeveloped());
    }

    @Test
    void setDevelopedTest_Success() {
        task.setDeveloped(false);
        assertFalse(task.isDeveloped());
        task.setDeveloped(true);
        assertTrue(task.isDeveloped());
    }

    @Test
    void isTestedTest_Success() {
        assertFalse(task.isTested());
    }

    @Test
    void setTestedTest_Success() {
        task.setTested(true);
        assertTrue(task.isTested());
        task.setTested(false);
        assertFalse(task.isTested());

    }


}