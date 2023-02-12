package ru.sberbank.data;

public class Task {
    private final int id;
    private boolean isDeveloped;
    private boolean isTested;
    private final String summary;

    public Task(int id, String summary) {
        this.id = id;
        this.isDeveloped = false;
        this.isTested = false;
        this.summary = summary;
    }

    public int getId() {
        return id;
    }

    public boolean isDeveloped() {
        return isDeveloped;
    }

    public void setDeveloped(boolean developed) {
        isDeveloped = developed;
    }

    public boolean isTested() {
        return isTested;
    }

    public void setTested(boolean tested) {
        isTested = tested;
    }

    public String getSummary() {
        return summary;
    }
}