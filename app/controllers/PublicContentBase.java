package controllers;


import helpers.HashUtils;
import models.User;
import play.mvc.Controller;
import java.util.UUID;

public class PublicContentBase extends Controller {

    public static void register() {
    	String csrfToken = UUID.randomUUID().toString();
    	session.put("csrfToken", csrfToken);
    	render(csrfToken);
    }


    public static void processRegister(String username, String password,
                                   String passwordCheck, String type,
                                   String csrfToken) {

    	String sessionToken = session.get("csrfToken");

    	if (sessionToken == null || csrfToken == null || !sessionToken.equals(csrfToken)) {
        forbidden("Invalid CSRF token");
    	}

    	// Invalidar token tras uso
    	session.remove("csrfToken");

    	// Validaciones mínimas adicionales
   	if (!password.equals(passwordCheck)) {
        	error("Passwords do not match");
    	}

    	// HARDENING: evitar escalada de privilegios
    	if (!"student".equals(type)) {
        	forbidden("Invalid user type");
    	}

    	User u = new User(username, HashUtils.getMd5(password), type, -1);
    	u.save();

    	registerComplete();
    }

    public static void registerComplete(){
        render();
    }

}
