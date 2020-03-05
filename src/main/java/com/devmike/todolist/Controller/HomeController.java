package com.devmike.todolist.Controller;

import com.devmike.todolist.Model.ToDo;
import com.devmike.todolist.Model.ToDoList;
import com.devmike.todolist.Model.User;
import com.devmike.todolist.Service.ToDoService;
import com.devmike.todolist.Service.UserService;
import com.devmike.todolist.Tools.LoginToken;
import com.devmike.todolist.Tools.UserAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    private String INDEX = "index";
    private String REDIRECT = "redirect:/";

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserAuthenticator userAuthenticator;

    @Autowired
    UserService US;

    @Autowired
    ToDoService TDS;

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(userAuthenticator.isUser(user)) {
            log.info(user.toString());
            log.info("User authenticated...");
            List<ToDoList> toDoLists = TDS.loadToDoList(user);
            model.addAttribute("user", user);
            model.addAttribute("toDoLists", toDoLists);
            model.addAttribute("newToDoList", new ToDoList());
            model.addAttribute("newToDo", new ToDo());
            return INDEX;
        } else {
            log.info("No user found...");
            model.addAttribute("loginToken", new LoginToken());
            model.addAttribute("user", new User());
            return INDEX;
        }
    }

    @PostMapping("/")
    public String index(@ModelAttribute LoginToken loginToken, HttpSession session) {
        User user = US.getUser(loginToken);
        if(user.getId() != 0) {
            log.info("Login successful...");
            session.setAttribute("user", user);
            return REDIRECT;
        }
        log.error("Login error...");
        return REDIRECT;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return REDIRECT;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        if(US.addUser(user)) {
            return REDIRECT;
        }
        //TODO Add alerts/confimations
        return REDIRECT;
    }

    @PostMapping("/addToDoList")
    public String addToDoList(@ModelAttribute ToDoList toDoList, HttpSession session) {
        User user = (User) session.getAttribute("user");
        toDoList.setUserId(user.getId());
        if(TDS.addToDoList(toDoList)) {
            log.info("Add To Do List succeeded");
            return REDIRECT;
        } else {
            log.error("Add To Do List failed");
            return REDIRECT;
        }
    }

    @PostMapping("/addToDo")
    public String addToDo(@ModelAttribute ToDo toDo, @RequestParam int listId) {
        toDo.setToDoListId(listId);
        log.info(toDo.toString());
        if(TDS.addToDo(toDo)) {
            log.info("Add To Do suceeded");
            return REDIRECT;
        } else {
            log.error("Add To Do failed");
            return REDIRECT;
        }
    }

    @PostMapping("/moveToDo")
    public String moveToDo(@RequestParam int toDoListId, @RequestParam int toDoId) {
        ToDo toDo = TDS.findToDoById(toDoId);
        toDo.setToDoListId(toDoListId);
        log.info(toDo.toString());
        if(TDS.editToDo(toDo)) {
            log.info("Move To Do suceeded");
            return REDIRECT;
        } else {
            log.error("Move To Do failed");
            return REDIRECT;
        }
    }

    @PostMapping("/editToDo")
    public String editToDo(@ModelAttribute ToDo toDo) {
        log.info(toDo.toString());
        if(TDS.editToDo(toDo)) {
            log.info("Edit To Do suceeded");
            return REDIRECT;
        } else {
            log.error("Edit To Do failed");
            return REDIRECT;
        }
    }

    @PostMapping("/deleteToDo")
    public String deleteToDo(@RequestParam int toDoId) {
        if(TDS.deleteToDo(toDoId)) {
            log.info("Delete To Do suceeded");
            return REDIRECT;
        } else {
            log.error("Delete To Do failed");
            return REDIRECT;
        }
    }

    @PostMapping("/deleteToDoList")
    public String deleteToDoList(@RequestParam int toDoListId) {
        if(TDS.deleteToDoList(toDoListId)) {
            log.info("Delete To Do List suceeded");
            return REDIRECT;
        } else {
            log.error("Delete To Do List failed");
            return REDIRECT;
        }
    }

    @PostMapping("/changetodostate")
    public String tododone(@RequestParam int todoId, @RequestParam boolean todoBool) {
        ToDo toDo = new ToDo();
        toDo.setId(todoId);
        toDo.setDone(todoBool);
        if(TDS.changeToDoState(toDo)) {
            log.info("Change to do state succeded");
            return REDIRECT;
        } else {
            log.error("Change to do state failed");
            return REDIRECT;
        }
    }
}
