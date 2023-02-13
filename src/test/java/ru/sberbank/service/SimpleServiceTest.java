package ru.sberbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.TeamMember;
import ru.sberbank.data.Tester;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleServiceTest {
    SimpleService service;

    @BeforeEach
    void setUp() {
        service = new SimpleService(mock(DeveloperService.class), mock(TesterService.class), new TaskService());
        when(service.getDeveloperService().getListOfFree())
                .thenReturn(
                        new ArrayList<>(
                                List.of(new Developer(1, "Damir", "Iskakov"))
                        )
                );
        when(service.getTesterService().getListOfFree())
                .thenReturn(
                        new ArrayList<>(
                                List.of(new Tester(2, "Petr", "Czech"))
                        )
                );

        List<TeamMember> actual = service.simpleMethod();
    }

    @Test
    void simpleMethod() {
        List<TeamMember> actual = service.simpleMethod();
        assertEquals(2, actual.size());
    }
}