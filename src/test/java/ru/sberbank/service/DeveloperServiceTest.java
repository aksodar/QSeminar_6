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
    void getDeveloper_failCheckException() {
        String expectedException = "Разработчик не найден";
        Exception actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    service.get("Ivan", "Ivanov");
                }
        );

        assertEquals(expectedException, actualException.getMessage());
    }
}