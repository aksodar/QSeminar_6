package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessServiceTest {

    private ProcessService processService;
    private final int EXPECTED_TASK_ID = 0;
    private final int EXPECTED_TASK_ID_1 = 1;
    private final String EXPECTED_TASK_SUMMARY = "Тестовая задача";
    private DeveloperService developerService;
    private TaskService taskService;
    private TesterService testerService;
    ArrayList<Developer> developers = new ArrayList<>();
    ArrayList<Tester> testers = new ArrayList<>();


    @BeforeEach
    void setUp() {
        Task expectedTask = new Task(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY);

        DeveloperService developerService = mock(DeveloperService.class);
        TesterService testerService = mock(TesterService.class);
        TaskService taskService = mock(TaskService.class);

        when(taskService.getTask(EXPECTED_TASK_ID)).thenReturn(expectedTask);
        when(taskService.createTask(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY)).thenReturn(expectedTask);

        this.processService = new ProcessService(taskService, developerService, testerService);

        Task expectedFinalTask = new Task(EXPECTED_TASK_ID_1, EXPECTED_TASK_SUMMARY);
        expectedFinalTask.setDeveloped(true);
        expectedFinalTask.setTested(true);
        when(taskService.getTask(EXPECTED_TASK_ID_1)).thenReturn(expectedFinalTask);

        when(developerService.getListOfFree()).thenReturn(developers);
        when(testerService.getListOfFree()).thenReturn(testers);
    }

    @Test
    void createTask_successful(){
        int actual = processService.createTask(EXPECTED_TASK_SUMMARY);
        Assertions.assertEquals(0, actual);
    }

    @Test
    void pushStatusTask_FinalStatus_successful(){
        String expected = "Задача уже в финальном статусе!";

        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                ()-> {
                    processService.pushStatusTask(EXPECTED_TASK_ID_1);
                }
        );
        Assertions.assertEquals(expected, exception.getMessage());
    }

    @Test
    void pushStatusTask_noFreeDevs() {
        String expected = "Нет свободных разработчиков!";

        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                ()-> {
                    processService.pushStatusTask(EXPECTED_TASK_ID);
                }
        );
        Assertions.assertEquals(expected, exception.getMessage());
    }

    @Test
    void pushStatusTask_noFreeTesters() {
        String expected = "Нет свободных тестировщиков!";

        Developer developer1 = new Developer(1, "Nikolay", "Erohin");
        developers.add(developer1);

        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                ()-> {
                    processService.pushStatusTask(EXPECTED_TASK_ID);
                }
        );
        Assertions.assertEquals(expected, exception.getMessage());
    }

    @Test
    void pushStatus_isDevelopedAndTested() {
        Developer developer1 = new Developer(1, "Nikolay", "Erohin");
        developers.add(developer1);

        Tester tester1 = new Tester(1, "Elena", "Polozova");
        testers.add(tester1);

        Task actual = processService.pushStatusTask(0);

        Assertions.assertTrue(actual.isDeveloped());
        Assertions.assertTrue(actual.isTested());
    }
}