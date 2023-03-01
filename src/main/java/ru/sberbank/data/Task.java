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

    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", isDeveloped=" + isDeveloped +
               ", isTested=" + isTested +
               ", summary='" + summary + '\'' +
               '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Task task = (Task) o;

        if (id != task.id) {
            return false;
        }
        if (isDeveloped != task.isDeveloped) {
            return false;
        }
        if (isTested != task.isTested) {
            return false;
        }
        return summary != null ? summary.equals(task.summary) : task.summary == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (isDeveloped ? 1 : 0);
        result = 31 * result + (isTested ? 1 : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        return result;
    }
}