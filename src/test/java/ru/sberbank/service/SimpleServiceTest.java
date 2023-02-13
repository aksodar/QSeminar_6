package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.TeamMember;
import ru.sberbank.data.Tester;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleServiceTest {
    private SimpleService simpleService;

    @BeforeEach
    void setUp() {
        DeveloperService developerService = mock(DeveloperService.class);
        TesterService testerService = mock(TesterService.class);
        TaskService taskService = mock(TaskService.class);

        ArrayList<Developer> developers = new ArrayList<>();
        developers.add(new Developer(1, "Ivan", "Ivan"));

        ArrayList<Tester> testers = new ArrayList<>();
        testers.add(new Tester(1, "Petr", "Petr"));

        when(developerService.getListOfFree()).thenReturn(developers);
        when(testerService.getListOfFree()).thenReturn(testers);

        this.simpleService = new SimpleService(developerService, testerService, taskService);
    }

    @Test
    void simpleMethod() {
        ArrayList<TeamMember> actual = simpleService.simpleMethod();
        Assertions.assertEquals(2, actual.size());
    }
}
