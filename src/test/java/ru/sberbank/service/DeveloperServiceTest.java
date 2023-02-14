package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeveloperServiceTest {

    private DeveloperService service;

    @BeforeEach
    void setUp() {
        this.service = new DeveloperService();

    }

    @Test
    void getTest_failCheckException() {
        String exeptionMessage = "Разработчик не найден";
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    service.get("Иван", "Иванов");
                }
        );
        Assertions.assertEquals(exeptionMessage, exception.getMessage());
    }
}