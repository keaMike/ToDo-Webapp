package com.devmike.todolist;

import com.devmike.todolist.Model.ToDo;
import com.devmike.todolist.Model.ToDoList;
import com.devmike.todolist.Model.User;

import java.util.List;

public interface ToDoInterface {

    List loadToDoList(User user);

    boolean addToDoList(ToDoList toDoList);

    boolean addToDo(ToDo toDo);

    boolean editToDoList(ToDoList toDoList);

    boolean editToDo(ToDo toDo);

    boolean deleteToDoList(int toDoListId);

    boolean deleteToDo(int todoId);

    ToDo findToDoById(int id);
}
