package ru.sberbank.service;

import org.junit.jupiter.api.Test;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TesterServiceTest {

    private final TesterService testerService = new TesterService();

    @Test
    void createTest_failWithExceptionInputParameterNullForFirstName() {

        String expectedMessage = "Входные данные не валидны";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.create(1, null, "Ivanov");
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createTest_failWithExceptionInputParameterNullForSecondName() {

        String expectedMessage = "Входные данные не валидны";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.create(1, "Ivan", null);
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createTest_failWithExceptionInputParameterEmptyStringForFirstName() {

        String expectedMessage = "Входные данные не валидны";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.create(1, "", "Ivanov");
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createTest_failWithExceptionInputParameterEmptyStringForSecondName() {

        String expectedMessage = "Входные данные не валидны";

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.create(1, "Ivan", "");
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void createTest_successfully() {

        Tester expectedTester = new Tester(1, "Ivan", "Ivanov");

        Tester actualTester = testerService.create(1, "Ivan", "Ivanov");

        assertEquals(expectedTester, actualTester);
    }


    @Test
    void getTest_failWithExceptionInvalidInputParameterForName() {

        String firstName = "Igor";
        String secondName = "Ivanov";
        String expectedMessage = "Тестировщики не найдены";

        Tester tester = new Tester(1, "Ivan", "Ivanov");
        testerService.list.add(tester);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.get(firstName, secondName);
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getTest_failWithExceptionInvalidInputParameterForSecond() {

        String firstName = "Ivan";
        String secondName = "Yegorovich";
        String expectedMessage = "Тестировщики не найдены";

        Tester tester = new Tester(1, "Ivan", "Ivanov");
        testerService.list.add(tester);

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> {
                    testerService.get(firstName, secondName);
                });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getTest_successfully() {

        String firstName = "Ivan";
        String secondName = "Ivanov";

        Tester expectedTester = new Tester(1, "Ivan", "Ivanov");
        testerService.list.add(expectedTester);

        Tester actualTester = testerService.get(firstName, secondName);

        assertEquals(expectedTester, actualTester);
    }

    @Test
    void getListOfFree_successfully() {

        List<Tester> expectedFreeTesterList = new ArrayList<>();
        Tester testerFirst = new Tester(1, "Ivan", "Ivanov");
        Tester testerSecond = new Tester(2, "Igor", "Yegorovich");
        Task task = new Task(1, "Резюме");

        expectedFreeTesterList.add(testerFirst);

        testerFirst.addTask(task);
        testerFirst.checkTask();
        testerSecond.addTask(task);

        testerService.list.add(testerFirst);
        testerService.list.add(testerSecond);

        ArrayList<Tester> actualFreeTesterList = testerService.getListOfFree();

        assertIterableEquals(expectedFreeTesterList, actualFreeTesterList);
    }
}