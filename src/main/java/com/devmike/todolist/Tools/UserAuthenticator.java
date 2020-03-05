package com.devmike.todolist.Tools;

import com.devmike.todolist.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public boolean isUser(User user) {
        return user != null;
    }
}
