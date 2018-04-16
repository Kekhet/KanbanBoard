package sample;

import java.time.LocalDate;

public class Task {
    public String title;
    public LocalDate date;
    public String priority;
    public String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task(String title){
        this.title = title;
    }

    public Task(String title, LocalDate date, String priority, String description) {
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.description = description;
    }
}
