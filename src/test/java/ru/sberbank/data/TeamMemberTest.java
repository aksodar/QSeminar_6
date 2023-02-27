package ru.sberbank.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamMemberTest {

    static class AnyPerson extends TeamMember {

        public AnyPerson(int id, String firstName, String secondName) {
            super(id, firstName, secondName);
        }
    }

    final String FIRST_NAME = "Jane";
    final String SECOND_NAME = "Doe";

    AnyPerson anyPerson;
    Task task;
    @BeforeEach
    void setUp() {
        anyPerson = new AnyPerson(3456, FIRST_NAME, SECOND_NAME);
        task = new Task(5432, "Person-stub-task");
    }


    @Test
    void getCurrentTaskTest_Fail() {    // actually, this is redundant. The method is tested above
        assertNull(anyPerson.getCurrentTask());
    }


    @Test
    void addTaskTest_Fail() {
        String expected = "Нарушение процесса!";
        anyPerson.addTask(task);
        Exception exception = assertThrows(IllegalStateException.class,
                () -> anyPerson.addTask(task));
        assertEquals(expected, exception.getMessage());
    }


    @Test
    void getFirstNameTest_Success() {
        assertEquals(FIRST_NAME, anyPerson.getFirstName());
    }

    @Test
    void getSecondNameTest_Success() {
        assertEquals(SECOND_NAME, anyPerson.getSecondName());

    }

    @Test
    void isFreeTest_Default() {
        boolean expected = false;   // default value must be returned
        assertEquals(expected, anyPerson.isFree());
    }

    @Test
    void isFreeTestBusy_Default() {
        boolean expected = false;
        anyPerson.addTask(task);
        assertEquals(expected, anyPerson.isFree());
    }

    @Test
    void isFreeTestFree_Success() {
        boolean expected = true;
        anyPerson.addTask(task);
        anyPerson.release();
        assertEquals(expected, anyPerson.isFree());
    }



}