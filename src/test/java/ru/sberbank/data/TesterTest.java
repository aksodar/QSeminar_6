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

    @Test
    void getCurrentTaskTest_Fail() {    // actually, this is redundant. The method is tested above
        assertNull(tester.getCurrentTask());
    }


    @Test
    void addTaskTest_Fail() {
        String expected = "Нарушение процесса!";
        tester.addTask(task);
        Exception exception = assertThrows(IllegalStateException.class,
                () -> tester.addTask(task));
        assertEquals(expected, exception.getMessage());
    }


    @Test
    void getFirstNameTest_Success() {
        String expected = "John";
        assertEquals(expected, tester.getFirstName());
    }

    @Test
    void getSecondNameTest_Success() {
        String expected = "Doe";
        assertEquals(expected, tester.getSecondName());

    }

    @Test
    void isFreeTest_Default() {
        boolean expected = false;   // default value must be returned
        assertEquals(expected, tester.isFree());
    }

    @Test
    void isFreeTestBusy_Default() {
        boolean expected = false;
        tester.addTask(task);
        assertEquals(expected, tester.isFree());
    }

   @Test
    void isFreeTestFree_Success() {
        boolean expected = true;
        tester.addTask(task);
        tester.checkTask();
        assertEquals(expected, tester.isFree());
    }


}