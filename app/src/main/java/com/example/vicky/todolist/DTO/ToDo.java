package com.example.vicky.todolist.DTO;

import java.util.ArrayList;
import java.util.List;

public class ToDo {
    private long id = -1;
    private String name = "";
    private String contents = "";
    private String date = "";
    private String createdAt = "";
    private boolean todo_isCompleted;
    private List items = (List)(new ArrayList());

    public final long getId() {
        return this.id;
    }

    public final void setId(long var1) {
        this.id = var1;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName( String name) {
        this.name = name;
    }

    public boolean getTodo_isCompleted() {
        return todo_isCompleted;
    }

    public void setTodo_isCompleted(boolean todo_isCompleted) {
        this.todo_isCompleted = todo_isCompleted;
    }

    public final String getContents() {
        return contents;
    }

    public final void setContents(String contents) {
        this.contents = contents;
    }

    public final String getDate() {
        return date;
    }

    public final void setDate(String date) {
        this.date = date;
    }

    public final List getItems() {
        return this.items;
    }

    public final void setItems( List items) {
        this.items = items;
    }
}
