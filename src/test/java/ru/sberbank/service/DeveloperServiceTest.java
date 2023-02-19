package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperServiceTest {

    @Test
    void getTest_failCheckException() {
        DeveloperService developerService = new DeveloperService();
        String expectedString = "Разработчик не найден";
        Exception e = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    developerService.get("User", "Name");
                }
                );
        Assertions.assertEquals(expectedString, e.getMessage());
    }
}