package com.mycompany.thei.hr.system.web;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {
    private String username;
    private String password;
    private boolean loggedIn;

    public String login() {
        if ("hr".equals(username) && "password".equals(password)) {
            loggedIn = true;
            return "index?faces-redirect=true";
        }
        return "login";
    }

    public String logout() {
        loggedIn = false;
        return "login?faces-redirect=true";
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }
}
