package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Tester;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class TesterServiceTest {
    private TesterService testerService;

    @BeforeEach
    void setUp() {
        testerService = new TesterService();
    }

    @Test
    void createTesterTest_failWithException() {
        //expected
        String expectedMessage = "Входные данные не валидны";
        //actual
        List<IllegalStateException> actualLIst = Arrays.asList(
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.create(1, null, null)
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.create(1, "", "")
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.create(1, "Name", "")
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.create(1, "Name", null)
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.create(1, null, "lastname")
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.create(1, "", "lastname")
                )
        );
        //result
        actualLIst.forEach(e -> Assertions.assertEquals(e.getMessage(), expectedMessage));
    }

    @Test
    void createTesterTest_successfully() {
        //expected
        Tester expected = new Tester(1, "太阳", "ساطع");
        //actual
        Tester actual = testerService.create(1, "太阳", "ساطع");
        //result
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getTesterTest_failWithNotValidException() {
        //data
        Tester data = testerService.create(2, "Name", "Lastname");
        //expected
        String expectedMessage = "Входные данные не валидны";
        //actual
        List<IllegalStateException> actualLIst = Arrays.asList(
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.get(null, null)
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.get("", "")
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.get("Name", "")
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.get("Name", null)
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.get(null, "lastname")
                ),
                Assertions.assertThrows(
                        IllegalStateException.class,
                        () -> testerService.get("", "lastname")
                )
        );
        //result
        actualLIst.forEach(e -> Assertions.assertEquals(e.getMessage(), expectedMessage));
    }

    @Test
    void getTesterTest_failWithNotFoundException() {
        //expected
        String expectedMessage = "Тестировщики не найдены";
        //actual
        IllegalStateException actual = Assertions.assertThrows(
                IllegalStateException.class,
                () -> testerService.get("Name", "Lastname")
        );
        //result
        Assertions.assertEquals(expectedMessage, actual.getMessage());
    }

    @Test
    void getTesterTest_successfully() {
        //data
        Tester one = testerService.create(1, "One", "Tester");
        Tester two = testerService.create(2, "One", "Tester");
        //expected
        Tester expected = new Tester(1, "One", "Tester");
        //actual
        Tester actual = testerService.get("One", "Tester");
        //result
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void getListOfFreeTest_successfully() {
        //expected
        List<Tester> expected = Collections.emptyList();
        //actual
        List<Tester> actual = testerService.getListOfFree();
        //result
        Assertions.assertIterableEquals(actual, expected);
    }
}