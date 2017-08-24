package com.codepath.todoapp;
import java.util.Date;
/**
 * Created by rashmisharma on 8/16/17.
 */

public class Reminder {

    private String name;
    private Long  id;
    private Date date;
    private int priority;

    public Date getDate() {
        return date;
    }


    public int getPriority() {
        return priority;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
