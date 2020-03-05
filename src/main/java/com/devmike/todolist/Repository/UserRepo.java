package com.devmike.todolist.Repository;

import com.devmike.todolist.Model.User;
import com.devmike.todolist.Tools.LoginToken;
import com.devmike.todolist.Tools.MySQLConnector;
import com.devmike.todolist.UserInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepo implements UserInterface {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MySQLConnector mySQLConnector;

    private PreparedStatement pstm;

    @Override
    public boolean addUser(User user) {
        log.info("Adding user init...");
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "INSERT INTO users (user_name, user_password, user_email)" +
                        "VALUES (?, ?, ?)"
            );
            pstmFulfilment(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean editUser(User user) {
        log.info("Editing user init...");
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "UPDATE users SET (user_name, user_password, user_email)" +
                        "VALUES (?, ? ,?)"
            );
            pstmFulfilment(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId) {
        log.info("Deleting user init...");
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "DELETE users WHERE user_id = ?"
            );
            pstm.setInt(1, userId);
            pstm.executeUpdate();
            pstm.close();
            mySQLConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUser(LoginToken loginToken) {
        log.info("Getting user init...");
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "SELECT * FROM users WHERE user_email = ? AND user_password = ?"
            );
            pstm.setString(1, loginToken.getEmail());
            pstm.setString(2, loginToken.getPassword());
            ResultSet rs = pstm.executeQuery();
            return userFulfilment(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void pstmFulfilment(User user) {
        try {
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            pstm.setString(3, user.getEmail());
            pstm.executeUpdate();
            pstm.close();
            mySQLConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User userFulfilment(ResultSet rs) {
        try {
            User user = new User();
            while(rs.next()) {
                user.setId(rs.getInt("user_id"));
                user.setUsername(rs.getString("user_name"));
                user.setPassword(rs.getString("user_password"));
                user.setEmail(rs.getString("user_email"));
            }
            log.info("Getting: " + user.toString());
            pstm.close();
            mySQLConnector.closeConnection();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
