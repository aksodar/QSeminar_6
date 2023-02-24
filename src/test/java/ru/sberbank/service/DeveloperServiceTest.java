package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperServiceTest {

    DeveloperService developerService;
    final int ID_A = 4356;
    final String FIRST_NAME_A = "John";
    final String SECOND_NAME_A = "Doe";

    final int ID_B = 6534;
    final String FIRST_NAME_B = "Jane";
    final String SECOND_NAME_B = "Daw";

//    final int ID_C = 6534;
//    final String FIRST_NAME_C = "Jane";
//    final String SECOND_NAME_C = "Goe";

    @BeforeEach
    void setUp() {
        developerService = new DeveloperService();
    }

    @Test
    void createTest_FailFirstNull() {
        String expected = "Входные данные не валидны";
        Exception exception;
        exception = assertThrows(IllegalStateException.class,
                () -> developerService.create(ID_A, null, SECOND_NAME_A));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void createTest_FailFirstEmpty() {
        String expected = "Входные данные не валидны";
        Exception exception;
        exception = assertThrows(IllegalStateException.class,
                () -> developerService.create(ID_A, "", SECOND_NAME_A));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void createTest_FailSecondNull() {
        String expected = "Входные данные не валидны";
        Exception exception;
        exception = assertThrows(IllegalStateException.class,
                () -> developerService.create(ID_A, FIRST_NAME_A, null));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void createTest_FailSecondEmpty() {
        String expected = "Входные данные не валидны";
        Exception exception;
        exception = assertThrows(IllegalStateException.class,
                () -> developerService.create(ID_A, FIRST_NAME_A, ""));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void createTest_Success() {
        Developer expected = new Developer(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        assertEquals(expected, developerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A));
    }

    @Test
    void getTest_Success() {
        Developer expected = new Developer(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        developerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        developerService.create(ID_B, FIRST_NAME_B, SECOND_NAME_B);
        assertEquals(expected, developerService.get(FIRST_NAME_A, SECOND_NAME_A));

        expected = new Developer(ID_B, FIRST_NAME_B, SECOND_NAME_B);
        assertEquals(expected, developerService.get(FIRST_NAME_B, SECOND_NAME_B));
    }

    @Test
    void getTest_Fail() {
        String expected = "Разработчик не найден";
        developerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        developerService.create(ID_B, FIRST_NAME_B, SECOND_NAME_B);
        Exception exception = assertThrows(IllegalStateException.class,
                () -> developerService.get(FIRST_NAME_A, SECOND_NAME_B));
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void getListOfFreeTest_SuccessEmptyList() {
        List<Developer> expected = new ArrayList<>();   // created developer isn't free, so the liat obtained is empty
        developerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        developerService.create(ID_B, FIRST_NAME_B, SECOND_NAME_B);
        assertEquals(expected, developerService.getListOfFree());
    }

    @Test
    void getListOfFreeTest_SuccessFilledListOfOne() {
        Task stubTask = new Task(1, "Stub-task");
        List<Developer> expected = new ArrayList<>(List.of(new Developer(ID_A, FIRST_NAME_A, SECOND_NAME_A)));

        developerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        developerService.get(FIRST_NAME_A, SECOND_NAME_A).addTask(stubTask);
        developerService.get(FIRST_NAME_A, SECOND_NAME_A).makeTask();

        developerService.create(ID_B, FIRST_NAME_B, SECOND_NAME_B);
        assertEquals(expected, developerService.getListOfFree());
    }

    @Test
    void getListOfFreeTest_SuccessFilledListOfTwo() {

        Task stubTask = new Task(1, "Stub-task");
        List<Developer> expected = new ArrayList<>(
                List.of(new Developer(ID_A, FIRST_NAME_A, SECOND_NAME_A),
                        new Developer(ID_B, FIRST_NAME_B, SECOND_NAME_B)
                ));

        developerService.create(ID_B, FIRST_NAME_B, SECOND_NAME_B);
        developerService.get(FIRST_NAME_B, SECOND_NAME_B).addTask(stubTask);
        developerService.get(FIRST_NAME_B, SECOND_NAME_B).makeTask();

        developerService.create(ID_A, FIRST_NAME_A, SECOND_NAME_A);
        developerService.get(FIRST_NAME_A, SECOND_NAME_A).addTask(stubTask);
        developerService.get(FIRST_NAME_A, SECOND_NAME_A).makeTask();

        List<Developer> actual = developerService.getListOfFree();
        assertNotEquals(expected, actual);
        assertEquals(expected.size(), actual.size());
    }
}