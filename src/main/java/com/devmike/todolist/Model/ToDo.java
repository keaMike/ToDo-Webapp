package com.devmike.todolist.Model;

public class ToDo {

    private int id;
    private int toDoListId;
    private String name;
    private String msg;
    private boolean done;

    public ToDo() {
    }

    public ToDo(int id, int toDoListId, String name, String msg, boolean done) {
        this.id = id;
        this.toDoListId = toDoListId;
        this.name = name;
        this.msg = msg;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToDoListId() {
        return toDoListId;
    }

    public void setToDoListId(int toDoListId) {
        this.toDoListId = toDoListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", toDoListId=" + toDoListId +
                ", name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", done=" + done +
                '}';
    }
}
