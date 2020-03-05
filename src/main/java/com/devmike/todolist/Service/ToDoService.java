package com.devmike.todolist.Service;

import com.devmike.todolist.Model.ToDo;
import com.devmike.todolist.Model.ToDoList;
import com.devmike.todolist.Model.User;
import com.devmike.todolist.Repository.ToDoRepo;
import com.devmike.todolist.ToDoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService implements ToDoInterface {

    @Autowired
    ToDoRepo TDR;

    @Override
    public List<ToDoList> loadToDoList(User user) {
        return TDR.loadToDoList(user);
    }

    @Override
    public boolean addToDoList(ToDoList toDoList) {
        return TDR.addToDoList(toDoList);
    }

    @Override
    public boolean addToDo(ToDo toDo) {
        return TDR.addToDo(toDo);
    }

    @Override
    public boolean editToDoList(ToDoList toDoList) {
        return TDR.editToDoList(toDoList);
    }

    @Override
    public boolean editToDo(ToDo toDo) {
        return TDR.editToDo(toDo);
    }

    @Override
    public boolean deleteToDoList(int toDoListId) {
        return TDR.deleteToDoList(toDoListId);
    }

    @Override
    public boolean deleteToDo(int todoId) {
        return TDR.deleteToDo(todoId);
    }

    @Override
    public ToDo findToDoById(int id) {
        return TDR.findToDoById(id);
    }

    public boolean changeToDoState(ToDo toDo) {
        return TDR.changeToDoState(toDo);
    }
}
