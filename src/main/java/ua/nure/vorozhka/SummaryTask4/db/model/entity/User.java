package ua.nure.vorozhka.SummaryTask4.db.model.entity;

import ua.nure.vorozhka.SummaryTask4.db.model.constant.Role;

import java.io.Serializable;

/**
 * Created by Stanislav on 09.01.2017.
 */
public class User implements Serializable {

    private String login;
    private String password;
    private String fullName;
    private Role role;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "login - " + login +
                ", password - " + password +
                ", fullName - " + fullName +
                ", role - " + role.getName();
    }
}
