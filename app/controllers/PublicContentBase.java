package controllers;


import helpers.HashUtils;
import models.User;
import play.mvc.Controller;

public class PublicContentBase extends Controller {

    
    public static void register(){
        render();
    }

    public static void processRegister(String username, String password, String passwordCheck, String type){
        String salt = HashUtils.generateSalt();
        String hashedPassword = HashUtils.hashPassword(password, salt);
        
        User u = new User(username, hashedPassword, type, -1);
        u.setSalt(salt);
        u.save();
        registerComplete();
    }

    public static void registerComplete(){
        render();
    }

}
