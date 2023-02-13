package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Tester;

import java.util.Arrays;
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
    void createTester_successfully() {
        //expected
        Tester expected = new Tester(1, "太阳", "ساطع");
        //actual
        Tester actual = testerService.create(1, "太阳", "ساطع");
        //result
        Assertions.assertEquals(expected, actual);
    }
}