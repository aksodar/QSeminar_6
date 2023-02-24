package ru.sberbank.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TesterTest {

    Tester tester;  //int id, String firstName, String secondName
    Task task;

    @BeforeEach
    void setUp() {
        tester = new Tester(1234, "John", "Doe");
        task = new Task(4321, "Task-stub");
    }

    @Test
    void checkTaskTest_Fail() {
        String expected = "Нет задания для исполнения!";
        Exception exception = assertThrows(IllegalStateException.class,
                () -> tester.checkTask());
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void checkTaskTest_Success() {
        tester.addTask(task);
        assertEquals(task, tester.checkTask());
    }

}