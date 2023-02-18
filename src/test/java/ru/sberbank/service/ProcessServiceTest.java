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
    DeveloperService developerService;
    TesterService testerService;
    TaskService taskService;
    private final int EXPECTED_TASK_ID_0= 0;
    private final int EXPECTED_TASK_ID_1 = 1;
    private final String EXPECTED_TASK_SUMMARY = "Тестовая задача";

    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class);
        testerService = mock(TesterService.class);
        developerService = mock(DeveloperService.class);

        Task expectedTask = new Task(EXPECTED_TASK_ID_0, EXPECTED_TASK_SUMMARY);
        expectedTask.setDeveloped(false);
        expectedTask.setTested(false);

        Task expectedTask2 = new Task(EXPECTED_TASK_ID_1, EXPECTED_TASK_SUMMARY);
        expectedTask2.setDeveloped(true);
        expectedTask2.setTested(true);

        ArrayList<Developer> developers = new ArrayList<>();
        ArrayList<Tester> testers = new ArrayList<>();


        when(taskService.createTask(EXPECTED_TASK_ID_0, EXPECTED_TASK_SUMMARY)).thenReturn(expectedTask);
        when(taskService.getTask(EXPECTED_TASK_ID_0)).thenReturn(expectedTask);
        when(taskService.getTask(EXPECTED_TASK_ID_1)).thenReturn(expectedTask2);
        when(developerService.getListOfFree()).thenReturn(developers);
        when(testerService.getListOfFree()).thenReturn(testers);

        this.processService = new ProcessService(taskService, developerService, testerService);
    }

    @Test
    void createTask_successful(){
        int actual = processService.createTask(EXPECTED_TASK_SUMMARY);
        Assertions.assertEquals(0, actual);
    }

    @Test
    void pushStatusTask_statusFinal(){
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
    void pushStatusTask_noFreeDevelopers(){
        String expected = "Нет свободных разработчиков!";

        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                ()-> {
                    processService.pushStatusTask(EXPECTED_TASK_ID_0);
                }
        );
        Assertions.assertEquals(expected, exception.getMessage());
    }

    @Test
    void pushStatusTask_noFreeTesters(){
        //не понимаю как эту часть можно вынести - если конечно можно (87-92 строки)
        ArrayList<Developer> developers = new ArrayList<>();
        Developer developer1 = new Developer(1, "Ivan", "Ivan");
        developer1.setFree(true);
        developers.add(developer1);

        when(developerService.getListOfFree()).thenReturn(developers);

        String expected = "Нет свободных тестировщиков!";

        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                ()-> {
                    processService.pushStatusTask(EXPECTED_TASK_ID_0);
                }
        );
        Assertions.assertEquals(expected, exception.getMessage());

    }

    @Test
    void pushStatusTask_successful(){
        //не понимаю как эту часть можно вынести - если конечно можно (109-121 строки)
        ArrayList<Developer> developers = new ArrayList<>();
        Developer developer1 = new Developer(1, "Ivan", "Ivan");
        developer1.setFree(true);
        developers.add(developer1);

        when(developerService.getListOfFree()).thenReturn(developers);

        ArrayList<Tester> testers = new ArrayList<>();
        Tester tester1 = new Tester(1, "Elena", "Elena");
        tester1.setFree(true);
        testers.add(tester1);

        when(testerService.getListOfFree()).thenReturn(testers);

        Task actual = processService.pushStatusTask(0);

        Assertions.assertTrue(actual.isDeveloped());
        Assertions.assertTrue(actual.isTested());

    }

}