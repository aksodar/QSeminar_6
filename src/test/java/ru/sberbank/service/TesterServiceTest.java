package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Tester;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TesterServiceTest {

    @Test
    void createTest_failWithException() {
        String expectedMessage = "Входные данные не валидны";
        TesterService testerService = new TesterService();
        TesterService testerService1 = new TesterService();
        TesterService testerService2 = new TesterService();

        IllegalStateException illegalStateException = assertThrows((IllegalStateException.class),
                () -> {
                    testerService.create(1, null, null);
                });
        Assertions.assertEquals(expectedMessage, illegalStateException.getMessage());
        IllegalStateException illegalStateException1 = assertThrows((IllegalStateException.class),
                () -> {
                    testerService1.create(1, "firstName", null);
                });
        Assertions.assertEquals(expectedMessage, illegalStateException1.getMessage());
        IllegalStateException illegalStateException2 = assertThrows((IllegalStateException.class),
                () -> {
                    testerService2.create(1, null, "secondName");
                });
        Assertions.assertEquals(expectedMessage, illegalStateException2.getMessage());
    }
    @Test
    void createTest_succesfully() {
        Tester expectedTester = new Tester(1, "firstName", "secondName");
        TesterService testerService = new TesterService();
        Tester actualTester = testerService.create(1, "firstName", "secondName");
        Assertions.assertEquals(expectedTester, actualTester);
    }

    @Test
    void getTest_failWithException() {
        String expectedMessage = "Тестировщики не найдены";
        TesterService testerService = new TesterService();
        IllegalStateException illegalStateException = assertThrows((IllegalStateException.class),
                () -> {
                    testerService.get("firstName", "secondName");
                });
        Assertions.assertEquals(expectedMessage, illegalStateException.getMessage());
    }

    @Test
    void getListOfFreeTest_succesfully() {
        TesterService testerService = new TesterService();
        ArrayList<Tester> expectedList = new ArrayList<>();
        ArrayList<Tester> actualList = testerService.getListOfFree();
        Assertions.assertEquals(expectedList, actualList);
    }
}