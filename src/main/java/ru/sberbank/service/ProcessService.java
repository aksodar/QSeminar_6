package ru.sberbank.service;

import ru.sberbank.data.Developer;
import ru.sberbank.data.Task;
import ru.sberbank.data.Tester;

import java.util.List;

public class ProcessService {

    private final TaskService taskService;
    private final DeveloperService developerService;
    private final TesterService testerService;

    public ProcessService(TaskService taskService, DeveloperService developerService, TesterService testerService) {
        this.taskService = taskService;
        this.developerService = developerService;
        this.testerService = testerService;
    }

    public int createTask(String summary) throws IllegalStateException {
        int id = taskService.getTasks().size();
        Task createdTask = taskService.createTask(id, summary);
        return createdTask.getId();
    }

    public Task pushStatusTask(int id){
        Task task = taskService.getTask(id);
        if(task.isDeveloped() && task.isTested()){
            throw new IllegalStateException("Задача уже в финальном статусе!");
        }

        if(!task.isDeveloped()){
            List<Developer> developers = developerService.getListOfFree();
            if(developers.isEmpty()){
                throw new IllegalStateException("Нет свободных разработчиков!");
            }
            Developer developer = developers.get(0);
            developer.addTask(task);
            task = developer.makeTask();
        }

        if(!task.isTested()){
            List<Tester> testers = testerService.getListOfFree();
            if(testers.isEmpty()){
                throw new IllegalStateException("Нет свободных тестировщиков!");
            }
            Tester tester = testers.get(0);
            tester.addTask(task);
            task = tester.checkTask();
        }

        return task;
    }
}
