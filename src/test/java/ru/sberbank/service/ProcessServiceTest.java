package ru.sberbank.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.data.Developer;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessServiceTest {

    private ProcessService processService;
    private final int EXPECTED_TASK_ID = 0;
    private final int EXPECTED_FINAL_TASK_ID = 1;
    private final String EXPECTED_TASK_SUMMARY = "Тестовая задача";
    private ArrayList<Developer> developers = new ArrayList<>();
    private ArrayList<Tester> testers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Task expectedTask = new Task(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY);

        TaskService taskService = mock(TaskService.class);
        when(taskService.createTask(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY)).thenReturn(expectedTask);
        when(taskService.getTask(EXPECTED_TASK_ID)).thenReturn(expectedTask);

        Task expectedTaskFinal = new Task(EXPECTED_FINAL_TASK_ID, EXPECTED_TASK_SUMMARY);
        expectedTaskFinal.setDeveloped(true);
        expectedTaskFinal.setTested(true);

        when(taskService.getTask(EXPECTED_FINAL_TASK_ID)).thenReturn(expectedTaskFinal);

        DeveloperService developerService = mock(DeveloperService.class);

        TesterService testerService = mock(TesterService.class);

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
    void pushStatusTask_failStatusException() {
        String expectedString = "Задача уже в финальном статусе!";
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_FINAL_TASK_ID);
                }
        );
        assertEquals(expectedString, exception.getMessage());
    }

    @Test
    void pushStatusTask_failDevelopersException() {
        String expectedString = "Нет свободных разработчиков!";
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK_ID);
                }
        );
        assertEquals(expectedString, exception.getMessage());
    }

    @Test
    void pushStatusTask_failTestersException() {
        String expectedString = "Нет свободных тестировщиков!";
        developers.add(new Developer(1, "Ivan", "Ivanov"));
        Exception exception = Assertions.assertThrows(
                IllegalStateException.class,
                () -> {
                    processService.pushStatusTask(EXPECTED_TASK_ID);
                }
        );
        assertEquals(expectedString, exception.getMessage());
    }

    @Test
    void pushStatusTask_successful(){
        developers.add(new Developer(1, "Ivan", "Ivanov"));
        testers.add(new Tester(1, "Petr", "Petrov"));
        Task expected = new Task(EXPECTED_TASK_ID, EXPECTED_TASK_SUMMARY);
        expected.setDeveloped(true);
        expected.setTested(true);
        Assertions.assertEquals(expected, processService.pushStatusTask(EXPECTED_TASK_ID));
    }
}