package ru.sberbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TesterServiceTest {

    TesterService testerService;
    final int ID_A = 4356;
    final String FIRST_NAME_A = "John";
    final String SECOND_NAME_A = "Doe";

    final int ID_B = 6534;
    final String FIRST_NAME_B = "Jane";
    final String SECOND_NAME_B = "Daw";

    @BeforeEach
    void setUp() {
        testerService = new TesterService();
    }

    @Test
    void create_TestFailFirstNull() {
        String expected = "Входные данные не валидны";
        Exception exception;
        exception = assertThrows(IllegalStateException.class,
                () -> testerService.create(ID_A, null, SECOND_NAME_A));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void create_TestFailSecondNull() {
        String expected = "Входные данные не валидны";
        Exception exception;
        exception = assertThrows(IllegalStateException.class,
                () -> testerService.create(ID_A, FIRST_NAME_A, null));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void create_TestFailFirstEmpty() {
        String expected = "Входные данные не валидны";
        Exception exception;
        exception = assertThrows(IllegalStateException.class,
                () -> testerService.create(ID_A, "", SECOND_NAME_A));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void create_TestFailSecondEmpty() {
        String expected = "Входные данные не валидны";
        Exception exception;
        exception = assertThrows(IllegalStateException.class,
                () -> testerService.create(ID_A, FIRST_NAME_A, ""));
        assertEquals(expected, exception.getMessage());
    }

   @Test
    void create_TestSuccess() {
       Tester expected = new Tester(ID_A, FIRST_NAME_A, SECOND_NAME_A);
       assertEquals(expected, testerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A));

   }

    @Test
    void get_TestSuccess() {    // A bug in Class TesterService, method get
        Tester expected = new Tester(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        testerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        assertEquals(expected, testerService.get(FIRST_NAME_A, SECOND_NAME_A));

        expected = new Tester(ID_B, FIRST_NAME_B, SECOND_NAME_B);
        assertEquals(expected, testerService.get(FIRST_NAME_B, SECOND_NAME_B));

    }
    @Test
    void get_TestFail() {
        String expected = "Тестировщики не найдены";
        testerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        testerService.create(ID_B, FIRST_NAME_B, SECOND_NAME_B);
        Exception exception = assertThrows(IllegalStateException.class,
                () -> testerService.get(FIRST_NAME_A, SECOND_NAME_B));
        assertEquals(expected, exception.getMessage());
    }


    @Test
    void getListOfFree_TestSuccess() {  // Unable to perform test
        Task stubTask = new Task(1, "Stub-task");
        List<Tester> expected = new ArrayList<>(
                List.of(new Tester(ID_A, FIRST_NAME_A, SECOND_NAME_A),
                        new Tester(ID_B, FIRST_NAME_B, SECOND_NAME_B)
                ));

        testerService.create(ID_B, FIRST_NAME_B, SECOND_NAME_B);
        testerService.get(FIRST_NAME_B, SECOND_NAME_B).addTask(stubTask);
        testerService.get(FIRST_NAME_B, SECOND_NAME_B).checkTask();

        testerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        testerService.get(FIRST_NAME_A, SECOND_NAME_A).addTask(stubTask);
        testerService.get(FIRST_NAME_A, SECOND_NAME_A).checkTask();

        List<Tester> actual = testerService.getListOfFree();
        assertNotEquals(expected, actual);
        assertEquals(expected.size(), actual.size());
    }

}
