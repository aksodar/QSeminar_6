package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperServiceTest {

    @Test
    void getTest_failCheckException() {
        DeveloperService developerService = new DeveloperService();
        String expectedString = "Разработчик не найден";
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    developerService.get("Damir", "Iskakov");
                }
        );
        assertEquals(expectedString, exception.getMessage());
    }
}