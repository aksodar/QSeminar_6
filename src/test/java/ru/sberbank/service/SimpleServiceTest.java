package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.TeamMember;
import ru.sberbank.data.Tester;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class SimpleServiceTest {
    private SimpleService service;

    @BeforeEach
    void setUp() {
        this.service = new SimpleService(new DeveloperService(), new TesterService(), new TaskService());
        DeveloperService developerService = mock(DeveloperService.class);
        TesterService testerService = mock(TesterService.class);
        TaskService taskService = mock(TaskService.class);

        ArrayList<Developer> developerList = new ArrayList<>();
        developerList.add(new Developer(1, "Ivan", "Ivanov"));
        developerList.add(new Developer(2, "Petr", "Petrov"));

        ArrayList<Tester> testers_list = new ArrayList<>();
        testers_list.add(new Tester(3, "Tester 1", "Tester 1"));
        testers_list.add(new Tester(4, "Tester 2", "Tester 2"));


        when(developerService.getListOfFree())
                .thenReturn(developerList);

        when(testerService.getListOfFree())
                .thenReturn(testers_list);

        this.service = new SimpleService(developerService, testerService, taskService);


    }

    @Test
    void simpleMethod() {

        ArrayList<TeamMember> actual = service.simpleMethod();
        Assertions.assertEquals(4, actual.size());

    }
}