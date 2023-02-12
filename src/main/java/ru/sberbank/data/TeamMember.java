package ru.sberbank.data;

public abstract class TeamMember {
    private Task currentTask;
    private final int id;
    private boolean isFree;
    private final String firstName;
    private final String secondName;

    public TeamMember(int id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public boolean isFree() {
        return isFree;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamMember that = (TeamMember) o;

        if (id != that.id) return false;
        if (!firstName.equals(that.firstName)) return false;
        return secondName.equals(that.secondName);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        return result;
    }

    public void addTask(Task task){
        if(!this.isFree && this.currentTask != null){
            throw new IllegalStateException("Нарушение процесса!");
        }
        this.currentTask = task;
        this.isFree = false;
    }

    void release(){
        this.currentTask = null;
        this.isFree = true;
    }
}
