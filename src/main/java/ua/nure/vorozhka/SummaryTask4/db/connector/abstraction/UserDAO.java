package ua.nure.vorozhka.SummaryTask4.db.connector.abstraction;

import ua.nure.vorozhka.SummaryTask4.db.model.constant.Role;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Stanislav on 18.01.2017.
 */
public abstract class UserDAO {

    public abstract boolean setUser(Connection connection, User user)
            throws SQLException;

    public abstract User getUser(
            Connection connection, String login, String password)
            throws SQLException;


    protected User getUser(
            ResultSet resultSet, String login, String password)
            throws SQLException {

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setFullName(resultSet.getString("full_name"));
        user.setRole(Role.getRole(resultSet.getInt("role_id")));

        return user;
    }
}
