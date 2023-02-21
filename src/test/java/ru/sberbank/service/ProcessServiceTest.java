package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessServiceTest {

    private ProcessService processService;
    private TaskService taskService;
    private DeveloperService developerService;
    private TesterService testerService;
    private final int EXPECTED_TASK_ID = 0;
    private final int EXPECTED_TASK1_ID = 1;
    private final String EXPECTED_TASK_SUMMARY = "Тестовая задача";

    @BeforeEach
    void setUp() {
        Task expectedTask = new Task(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY);
        Task expectedTask1 = new Task(EXPECTED_TASK1_ID, EXPECTED_TASK_SUMMARY);
        expectedTask1.setDeveloped(true);
        expectedTask1.setTested(true);

        this.taskService = mock(TaskService.class);
        when(taskService.createTask(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY)).thenReturn(expectedTask);
        when(taskService.getTask(EXPECTED_TASK_ID)).thenReturn(expectedTask);
        when(taskService.getTask(EXPECTED_TASK1_ID)).thenReturn(expectedTask1);

        this.developerService = mock(DeveloperService.class);

        this.testerService = mock(TesterService.class);

        this.processService = new ProcessService(taskService, developerService, testerService);
    }

    @Test
    void createTask_successful(){
        int actual = processService.createTask(EXPECTED_TASK_SUMMARY);
        assertEquals(0, actual);
    }

    @Test
    void pushStatusTask_successful(){
        ArrayList<Developer> developers = new ArrayList<>();
        Developer developer = new Developer(1, "Ivan", "Ivanov");
        developers.add(developer);
        when(developerService.getListOfFree()).thenReturn(developers);

        ArrayList<Tester> testers = new ArrayList<>();
        Tester tester = new Tester(1, "Peter", "Sidorov");
        testers.add(tester);
        when(testerService.getListOfFree()).thenReturn(testers);

        Task actual = processService.pushStatusTask(EXPECTED_TASK_ID);

        assertNotNull(actual);
        assertTrue(actual.isDeveloped());
        assertTrue(actual.isTested());
    }

    @Test
    void pushStatusTask_finalStatusCheckException() {
        String expectedException = "Задача уже в финальном статусе!";
        Exception actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK1_ID);
                }
        );

        assertEquals(expectedException, actualException.getMessage());
    }

    @Test
    void pushStatusTask_noFreeDevelopersCheckException() {
        String expectedException = "Нет свободных разработчиков!";
        Exception actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK_ID);
                }
        );

        assertEquals(expectedException, actualException.getMessage());
    }

    @Test
    void pushStatusTask_noFreeTestersCheckException() {
        ArrayList<Developer> developers = new ArrayList<>();
        Developer developer = new Developer(1, "Ivan", "Ivanov");
        developers.add(developer);
        when(developerService.getListOfFree()).thenReturn(developers);

        String expectedException = "Нет свободных тестировщиков!";
        Exception actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK_ID);
                }
        );

        assertEquals(expectedException, actualException.getMessage());
    }

}