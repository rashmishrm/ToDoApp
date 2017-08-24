package com.codepath.todoapp;
import java.util.Date;
/**
 * Created by rashmisharma on 8/16/17.
 */

public class Reminder {

    private String name;
    private int  id;
    private Date date;
    private String priority;

    public Reminder() {
    }

    public Reminder(String name, int id, Date date, String priority) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }


    public String getPriority() {
        return priority;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
