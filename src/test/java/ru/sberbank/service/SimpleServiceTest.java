package ru.sberbank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.Task;
import ru.sberbank.data.TeamMember;
import ru.sberbank.data.Tester;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleServiceTest {

    private SimpleService simpleService;

    @BeforeEach
    void setUp(){
        DeveloperService developerService = mock(DeveloperService.class);
        TesterService testerService = mock(TesterService.class);
        TaskService taskService = new TaskService();
        ArrayList<Developer> developers = new ArrayList<>();
        ArrayList<Tester> testers = new ArrayList<>();
        developers.add(new Developer(1, "Ivan", "Ivanov"));
        testers.add(new Tester(1, "Peter", "Sidorov"));
        when(developerService.getListOfFree()).thenReturn(developers);
        when(testerService.getListOfFree()).thenReturn(testers);
        this.simpleService = new SimpleService(developerService, testerService, taskService);
    }

    @Test
    void simpleMethod() {
        ArrayList<TeamMember> actual = simpleService.simpleMethod();
        assertEquals(2, actual.size());
    }
}