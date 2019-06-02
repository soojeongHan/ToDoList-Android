package com.example.vicky.todolist.DTO;

public class ToDoItem {

    private long id = -1L;
    private long toDoId = -1L;
    private String subContents = "";
    private String subDate = "";
    private String itemName = "";
    private boolean isCompleted;

    public final long getId() {
        return this.id;
    }

    public final void setId(long var1) {
        this.id = var1;
    }

    public final long getToDoId() {
        return this.toDoId;
    }

    public final void setToDoId(long var1) {
        this.toDoId = var1;
    }

    public final String getSubContents() {
        return subContents;
    }

    public final void setSubContents(String subContents) {
        this.subContents = subContents;
    }

    public final String getSubDate() {
        return subDate;
    }

    public final void setSubDate(String subDate) {
        this.subDate = subDate;
    }

    public final String getItemName() {
        return this.itemName;
    }

    public final void setItemName( String itemName) {
        this.itemName = itemName;
    }

    public final boolean isCompleted() {
        return this.isCompleted;
    }

    public final void setCompleted(boolean var1) {
        this.isCompleted = var1;
    }
}
