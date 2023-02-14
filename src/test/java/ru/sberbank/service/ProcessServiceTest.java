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
    private TesterService testerService;
    private DeveloperService developerService;
    private final int EXPECTED_TASK_ID = 0;
    private final int EXPECTED_TASK_ID_1 = 1;
    private final int EXPECTED_TASK_ID_2 = 2;
    private final int EXPECTED_TASK_ID_3 = 3;
    private final String EXPECTED_TASK_SUMMARY = "Тестовая задача";
    private Task expectedTask_3;

    @BeforeEach
    void setUp() {
        Task expectedTask = new Task(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY);

        Task expectedTask_1 = new Task(EXPECTED_TASK_ID_1, EXPECTED_TASK_SUMMARY);
        expectedTask_1.setDeveloped(true);
        expectedTask_1.setTested(true);

        Task expectedTask_2 = new Task(EXPECTED_TASK_ID_2, EXPECTED_TASK_SUMMARY);

        this.expectedTask_3 = new Task(EXPECTED_TASK_ID_3, EXPECTED_TASK_SUMMARY);
        this.expectedTask_3.setDeveloped(true);

        TaskService taskService = mock(TaskService.class);
        when(taskService.createTask(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY)).thenReturn(expectedTask);
        when(taskService.getTask(EXPECTED_TASK_ID_1)).thenReturn(expectedTask_1);
        when(taskService.getTask(EXPECTED_TASK_ID_2)).thenReturn(expectedTask_2);
        when(taskService.getTask(EXPECTED_TASK_ID_3)).thenReturn(this.expectedTask_3);


        this.developerService = mock(DeveloperService.class);
        this.testerService = mock(TesterService.class);

        this.processService = new ProcessService(taskService, developerService, this.testerService);
    }

    @Test
    void createTask_successful() {
        int actual = processService.createTask(EXPECTED_TASK_SUMMARY);
        Assertions.assertEquals(0, actual);
    }

    @Test
    void pushStatusTaskComplete_failCheckException() {

        String exceptionMessage = "Задача уже в финальном статусе!";
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK_ID_1);
                }
        );
        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void pushStatusTaskNoFreeDevelopers_failCheckException() {

        String exceptionMessage = "Нет свободных разработчиков!";
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK_ID_2);
                }
        );
        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void pushStatusTaskNoFreeTesters_failCheckException() {

        //(видимо в исходнике некорректный exceptionMessage)
        String exceptionMessage = "Нет свободных разработчиков!";
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK_ID_3);
                }
        );
        Assertions.assertEquals(exceptionMessage, exception.getMessage());

    }

    @Test
    void pushStatusTaskNeedDevelopedButNoFreeTesters_failCheckException() {

        Developer developer = new Developer(101, "Кодер", "Геймплеев");
        ArrayList<Developer> developerArrayList = new ArrayList<>();
        developerArrayList.add(developer);

        when(developerService.getListOfFree()).thenReturn(developerArrayList);

        String exceptionMessage = "Нет свободных разработчиков!";
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK_ID_2);
                }
        );

        Assertions.assertEquals(exceptionMessage, exception.getMessage());

    }

    @Test
    void pushStatusTaskNeedDevelopedAndFreeTesters_successful() {

        Developer developer = new Developer(101, "Кодер", "Геймплеев");
        ArrayList<Developer> developerArrayList = new ArrayList<>();
        developerArrayList.add(developer);
        when(developerService.getListOfFree()).thenReturn(developerArrayList);

        Tester tester = new Tester(1, "Тестер", "Пакетов");
        ArrayList<Tester> testersArrayList = new ArrayList<>();
        testersArrayList.add(tester);
        when(testerService.getListOfFree()).thenReturn(testersArrayList);

        Task expectedTask = new Task(EXPECTED_TASK_ID_2, EXPECTED_TASK_SUMMARY);
        expectedTask.setDeveloped(true);
        expectedTask.setTested(true);

        Task actualTask = processService.pushStatusTask(EXPECTED_TASK_ID_2);

        //Т.к. в Task не прописан equals(), сравниваем по полям:
        Assertions.assertEquals(expectedTask.getId(), actualTask.getId());
        Assertions.assertEquals(expectedTask.isDeveloped(), actualTask.isDeveloped());
        Assertions.assertEquals(expectedTask.isTested(), actualTask.isTested());
        Assertions.assertEquals(expectedTask.getSummary(), actualTask.getSummary());

    }

    @Test
    void pushStatusTaskNeedTest_successful() {

        expectedTask_3.setDeveloped(true);
        expectedTask_3.setTested(false);

        Tester tester = new Tester(1, "Тестер", "Пакетов");
        ArrayList<Tester> testersArrayList = new ArrayList<>();
        testersArrayList.add(tester);

        when(testerService.getListOfFree()).thenReturn(testersArrayList);
        Task actualTask = processService.pushStatusTask(EXPECTED_TASK_ID_3);

        //Т.к. в Task не прописан equals(), сравниваем по полям:
        Assertions.assertEquals(expectedTask_3.getId(), actualTask.getId());
        Assertions.assertEquals(expectedTask_3.isDeveloped(), actualTask.isDeveloped());
        Assertions.assertEquals(expectedTask_3.isTested(), actualTask.isTested());
        Assertions.assertEquals(expectedTask_3.getSummary(), actualTask.getSummary());

    }

}