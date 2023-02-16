package ru.sberbank.service;

import ru.sberbank.data.TeamMember;

import java.util.ArrayList;
import java.util.List;

public class SimpleService {

    private DeveloperService developerService;
    private TesterService testerService;
    private TaskService taskService;

    public SimpleService(DeveloperService developerService, TesterService testerService, TaskService taskService) {
        this.developerService = developerService;
        this.testerService = testerService;
        this.taskService = taskService;
    }

    public List<TeamMember> simpleMethod(){
        ArrayList<TeamMember> teamMembers = new ArrayList<>();

        teamMembers.addAll(developerService.getListOfFree());
        teamMembers.addAll(testerService.getListOfFree());

        return teamMembers;
    }

    public DeveloperService getDeveloperService() {
        return developerService;
    }

    public TesterService getTesterService() {
        return testerService;
    }

    public TaskService getTaskService() {
        return taskService;
    }
}