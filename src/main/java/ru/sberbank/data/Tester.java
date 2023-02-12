package ru.sberbank.data;

public class Tester extends TeamMember{
    public Tester(int id, String firstName, String secondName) {
        super(id, firstName, secondName);
    }

    public Task checkTask(){
        if(this.getCurrentTask() == null){
            throw new IllegalStateException("Нет задания для исполнения!");
        }
        Task task = this.getCurrentTask();
        task.setTested(true);

        this.release();

        return task;
    }
}
