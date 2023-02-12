package ru.sberbank.data;

public class Developer extends TeamMember {

    public Developer(int id, String firstName, String secondName) {
        super(id, firstName, secondName);
    }

    public Task makeTask(){
        if(this.getCurrentTask() == null){
            throw new IllegalStateException("Нет задания для исполнения!");
        }
        Task task = this.getCurrentTask();
        task.setDeveloped(true);

        this.release();

        return task;
    }
}
