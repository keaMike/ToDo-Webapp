package com.devmike.todolist.Service;

import com.devmike.todolist.Model.User;
import com.devmike.todolist.Repository.UserRepo;
import com.devmike.todolist.Tools.LoginToken;
import com.devmike.todolist.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService implements UserInterface {

    @Autowired
    UserRepo UR;

    @Value("${encryption.salt}")
    private String enctryptionSalt;


    @Override
    public boolean addUser(User user) {
        user.setPassword(encryptingPassword(user.getPassword()));
        return UR.addUser(user);
    }

    @Override
    public boolean editUser(User user) {
        return UR.editUser(user);
    }

    @Override
    public boolean deleteUser(int userId) {
        return UR.deleteUser(userId);
    }

    @Override
    public User getUser(LoginToken loginToken) {
        loginToken.setPassword(encryptingPassword(loginToken.getPassword()));
        return UR.getUser(loginToken);
    }

    private String encryptingPassword(String password) {
        if(password == null){
            return null;
        }

        //Insert your own salt below
        String saltedPassword = password + enctryptionSalt;

        try {
            //Message digests are one-way-hash functions, that take data and output a hash value.

            //Here we choose to use MD5.
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            //We put our string into the message digest
            //and tell it to start at the start of the string and end at the end.
            digest.update(saltedPassword.getBytes(), 0, saltedPassword.length());

            //Here we use the BigInteger class, with the message digest
            //to output a string in base 16 (hex).
            //Hex: uses symbols 0-9 and a-f.
            String encriptedPassword = new BigInteger(1, digest.digest()).toString(16);

            return encriptedPassword;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
