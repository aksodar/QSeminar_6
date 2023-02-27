package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessServiceTest {

    private ProcessService processService;

    private Task expectedTask;
    private final int EXPECTED_TASK_ID = 0;
    private final String EXPECTED_TASK_SUMMARY = "Тестовая задача";

    @BeforeEach
    void setUp() {
        expectedTask = new Task(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY);

        Developer developer = mock(Developer.class);
        Tester tester = mock(Tester.class);
        tester.addTask(new Task(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY));

//        developer.addTask(new Task(123, "Stub"));
//        developer.makeTask();
//        tester.addTask(new Task(123, "Stub"));
//        tester.checkTask();

        TaskService taskService = mock(TaskService.class);
        when(taskService.createTask(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY)).thenReturn(expectedTask);
        when(taskService.getTask(EXPECTED_TASK_ID)).thenReturn(expectedTask);


        DeveloperService developerService = mock(DeveloperService.class);
        when(developerService.getListOfFree()).thenReturn(
                new ArrayList<>(List.of(developer)));


        TesterService testerService = mock(TesterService.class);
        when(testerService.getListOfFree()).thenReturn(
                new ArrayList<>(List.of(tester)));



        this.processService = new ProcessService(taskService, developerService, testerService);
    }

    @Test
    void createTask_successful() {
        int actual = processService.createTask(EXPECTED_TASK_SUMMARY);
        Assertions.assertEquals(0, actual);
    }

    @Test
    void pushStatusTask_Fail() {
        /*
        * всё время падает с ошибкой:
        * java.lang.NullPointerException: Cannot invoke "ru.sberbank.data.Task.isTested()" because "task" is null
        * после добавления задачи разработчику (developer.addTask(task);),
        * задача обнуляется.
        * Не получается реализовать тест без вмешательства в исходный код.
        **/
        processService.createTask(EXPECTED_TASK_SUMMARY);
        Assertions.assertTrue(processService.pushStatusTask(EXPECTED_TASK_ID).isDeveloped());

    }


}