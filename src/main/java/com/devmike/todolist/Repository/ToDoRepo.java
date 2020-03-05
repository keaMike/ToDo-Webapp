package com.devmike.todolist.Repository;

import com.devmike.todolist.Model.ToDo;
import com.devmike.todolist.Model.ToDoList;
import com.devmike.todolist.Model.User;
import com.devmike.todolist.ToDoInterface;
import com.devmike.todolist.Tools.MySQLConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ToDoRepo implements ToDoInterface {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MySQLConnector mySQLConnector;

    private PreparedStatement pstm;

    @Override
    public List<ToDoList> loadToDoList(User user) {
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "SELECT * FROM todolist WHERE user_id = ?"
            );
            pstm.setInt(1, user.getId());
            ResultSet rs = pstm.executeQuery();
            List<ToDoList> listOfToDoLists = toDoListFulfillment(rs);
            if(listOfToDoLists != null) {
                for (ToDoList toDoList : listOfToDoLists) {
                    toDoList.setToDos(loadToDos(toDoList));
                }
            }
            return listOfToDoLists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addToDoList(ToDoList toDoList) {
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "INSERT INTO todolist (todolist_name, user_id)" +
                        "VALUES(?, ?)"
            );
            pstm.setString(1, toDoList.getName());
            pstm.setInt(2, toDoList.getUserId());
            if(pstm.executeUpdate() == 1) return true;
            pstm.close();
            mySQLConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addToDo(ToDo toDo) {
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "INSERT INTO todo (todo_name, todo_msg, todolist_id)" +
                        "VALUES (?, ?, ?)"
            );
            pstm.setString(1, toDo.getName());
            pstm.setString(2, toDo.getMsg());
            pstm.setInt(3, toDo.getToDoListId());
            if(pstm.executeUpdate() == 1) return true;
            mySQLConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean editToDoList(ToDoList toDoList) {
        return false;
    }

    @Override
    public boolean editToDo(ToDo toDo) {
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "UPDATE todo SET todo_name = ?, todo_msg = ?, todolist_id = ? WHERE todo_id = ?"
            );
            pstm.setString(1, toDo.getName());
            pstm.setString(2, toDo.getMsg());
            pstm.setInt(3, toDo.getToDoListId());
            pstm.setInt(4, toDo.getId());
            if(pstm.executeUpdate() == 1) return true;
            pstm.close();
            mySQLConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean deleteToDoList(int toDoListId) {
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "DELETE FROM todo WHERE todolist_id = ?"
            );
            pstm.setInt(1, toDoListId);
            pstm.executeUpdate();

            pstm = mySQLConnector.openConnection().prepareStatement(
                    "DELETE FROM todolist WHERE todolist_id = ?"
            );
            pstm.setInt(1, toDoListId);
            if(pstm.executeUpdate() == 1) return true;
            pstm.close();
            mySQLConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteToDo(int todoId) {
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "DELETE FROM todo WHERE todo_id = ?"
            );
            pstm.setInt(1, todoId);
            if(pstm.executeUpdate() == 1) return true;
            pstm.close();
            mySQLConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ToDo findToDoById(int id) {
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "SELECT * FROM todo WHERE todo_id = ?"
            );
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            return toDosFulfillment(rs).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ToDoList> toDoListFulfillment(ResultSet rs) {
        try {
            List<ToDoList> listWithToDoLists = new ArrayList<>();
            while(rs.next()) {
                ToDoList toDoList = new ToDoList();
                toDoList.setId(rs.getInt("todolist_id"));
                toDoList.setName(rs.getString("todolist_name"));
                toDoList.setUserId(rs.getInt("user_id"));
                listWithToDoLists.add(toDoList);
            }
            pstm.close();
            mySQLConnector.closeConnection();
            return listWithToDoLists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ToDo> loadToDos(ToDoList toDoList) {
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "SELECT * FROM todo WHERE todolist_id = ?"
            );
            pstm.setInt(1, toDoList.getId());
            ResultSet rs = pstm.executeQuery();
            return toDosFulfillment(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ToDo> toDosFulfillment(ResultSet rs) {
        try {
            List<ToDo> listWithToDos = new ArrayList<>();
            while(rs.next()) {
                ToDo toDo = new ToDo();
                toDo.setId(rs.getInt("todo_id"));
                toDo.setName(rs.getString("todo_name"));
                toDo.setMsg(rs.getString("todo_msg"));
                toDo.setDone(rs.getBoolean("todo_done"));
                toDo.setToDoListId(rs.getInt("todolist_id"));
                listWithToDos.add(toDo);
            }
            pstm.close();
            mySQLConnector.closeConnection();
            return listWithToDos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changeToDoState(ToDo toDo) {
        try {
            pstm = mySQLConnector.openConnection().prepareStatement(
                    "UPDATE todo SET todo_done = ? WHERE todo_id = ?"
            );
            pstm.setBoolean(1, !toDo.isDone());
            pstm.setInt(2, toDo.getId());
            if(pstm.executeUpdate() == 1) return true;
            pstm.close();
            mySQLConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
