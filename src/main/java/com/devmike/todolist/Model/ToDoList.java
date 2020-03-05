package com.devmike.todolist.Model;

import java.util.List;

public class ToDoList {

    private int id;
    private int userId;
    private String name;
    private List<ToDo> toDos;

    public ToDoList() {
    }

    public ToDoList(int id, int userId, String name, List<ToDo> toDos) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.toDos = toDos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ToDo> getToDos() {
        return toDos;
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }

    @Override
    public String toString() {
        return "ToDoList{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", toDos=" + toDos +
                '}';
    }
}
