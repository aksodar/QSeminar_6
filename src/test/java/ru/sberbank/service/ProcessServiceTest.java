package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessServiceTest {

    private ProcessService processService;
    private final int EXPECTED_TASK_ID = 0;
    private final int EXPECTED_TASK_ID_2 = 2;
    private final String EXPECTED_TASK_SUMMARY = "Тестовая задача";
    DeveloperService developerService;
    TesterService testerService;

    @BeforeEach
    void setUp() {
        Task expectedTask = new Task(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY);
        Task expectedFinishTask = new Task(EXPECTED_TASK_ID_2, EXPECTED_TASK_SUMMARY);
        expectedFinishTask.setDeveloped(true);
        expectedFinishTask.setTested(true);

        TaskService taskService = mock(TaskService.class);
        when(taskService.createTask(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY)).thenReturn(expectedTask);
        when(taskService.getTask(EXPECTED_TASK_ID_2)).thenReturn(expectedFinishTask);

        when(taskService.getTask(EXPECTED_TASK_ID)).thenReturn(expectedTask);

        developerService = mock(DeveloperService.class);
        testerService = mock(TesterService.class);

        this.processService = new ProcessService(taskService, developerService, testerService);
    }

    @Test
    void createTask_successful() {
        int actual = processService.createTask(EXPECTED_TASK_SUMMARY);
        Assertions.assertEquals(0, actual);
    }

    @Test
    void pushStatusTask_successful() {
        when(developerService.getListOfFree())
                .thenReturn(
                        new ArrayList<>(List.of(new Developer(1, "John", "Dow")))
                );

        when(testerService.getListOfFree())
                .thenReturn(
                        new ArrayList<>(List.of(new Tester(1, "John", "Dow")))
                );
        Task actual = processService.pushStatusTask(EXPECTED_TASK_ID);
        Assertions.assertTrue(actual.isDeveloped());
        Assertions.assertTrue(actual.isTested());
    }

    @Test
    void pushStatusTask_failFinishTask() {
        Exception actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK_ID_2);
                }
        );
        Assertions.assertEquals("Задача уже в финальном статусе!", actualException.getMessage());
    }

    @Test
    void pushStatusTask_failDevelopers() {
        Exception actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> processService.pushStatusTask(EXPECTED_TASK_ID)
        );
        Assertions.assertEquals("Нет свободных разработчиков!", actualException.getMessage());
    }

    @Test
    void pushStatusTask_failTester() {
        when(developerService.getListOfFree())
                .thenReturn(
                        new ArrayList<>(List.of(new Developer(1, "John", "Dow")))
                );
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> processService.pushStatusTask(EXPECTED_TASK_ID)
        );
        Assertions.assertEquals("Нет свободных тестировщиков!", exception.getMessage());
    }
}