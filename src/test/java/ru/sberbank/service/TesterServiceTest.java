package ru.sberbank.service;

import org.junit.jupiter.api.Test;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TesterServiceTest {

    @Test
    void createTestFailWithExceptionInputParameterNullForFirstName() {

        String expectedMessage = "Входные данные не валидны";

        TesterService testerService = new TesterService();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.create(1, null, "Ivanov");
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createTestFailWithExceptionInputParameterNullForSecondName() {

        String expectedMessage = "Входные данные не валидны";

        TesterService testerService = new TesterService();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.create(1, "Ivan", null);
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createTestFailWithExceptionInputParameterEmptyStringForFirstName() {

        String expectedMessage = "Входные данные не валидны";

        TesterService testerService = new TesterService();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.create(1, "", "Ivanov");
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createTestFailWithExceptionInputParameterEmptyStringForSecondName() {

        String expectedMessage = "Входные данные не валидны";

        TesterService testerService = new TesterService();

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.create(1, "Ivan", "");
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createTestSuccessfully() {

        Tester expectedTester = new Tester(1, "Ivan", "Ivanov");

        TesterService testerService = new TesterService();

        Tester actualTester = testerService.create(1, "Ivan", "Ivanov");

        assertEquals(expectedTester, actualTester);
    }


    @Test
    void getTestFailWithException() {
        String firstName = "Igor";
        String secondName = "Ivanov";
        String expectedMessage = "Тестировщики не найдены";

        TesterService testerService = new TesterService();

        Tester tester = new Tester(1, "Ivan", "Ivanov");
        testerService.list.add(tester);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.get(firstName, secondName);
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getTestSuccessfully() {
        String firstName = "Ivan";
        String secondName = "Ivanov";

        TesterService testerService = new TesterService();

        Tester expectedTester = new Tester(1, "Ivan", "Ivanov");
        testerService.list.add(expectedTester);

        Tester actualTester = testerService.get(firstName, secondName);

        assertEquals(expectedTester, actualTester);
    }

    @Test
    void getListOfFreeSuccessfully() {

        TesterService testerService = new TesterService();
        Tester expectedTester = new Tester(1, "Ivan", "Ivanov");
        Tester testerSecond = new Tester(2, "Igor", "Igorevich");
        Task task = new Task(1, "Резюме");

        expectedTester.addTask(task);
        expectedTester.checkTask();
        testerSecond.addTask(task);

        testerService.list.add(expectedTester);
        testerService.list.add(testerSecond);

        ArrayList<Tester> freeTester = testerService.getListOfFree();
        Tester actualTester = freeTester.get(0);

        assertEquals(expectedTester, actualTester);
    }
}