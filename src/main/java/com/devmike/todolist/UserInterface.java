package com.devmike.todolist;

import com.devmike.todolist.Model.User;
import com.devmike.todolist.Tools.LoginToken;

import java.sql.SQLException;

public interface UserInterface {
    boolean addUser(User user);

    boolean editUser(User user);

    boolean deleteUser(int userId);

    User getUser(LoginToken loginToken);
}
