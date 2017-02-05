package ua.nure.vorozhka.SummaryTask4.db.connector.postgres.dao;

import org.apache.commons.codec.digest.DigestUtils;
import ua.nure.vorozhka.SummaryTask4.db.connector.DAOUtils;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.UserDAO;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Stanislav on 12.01.2017.
 */
public final class UserDAOPostgresImpl extends UserDAO {

    private static final String SQL_SELECT_USER_BY_LOGIN_AND_PASS =
            "SELECT * FROM users WHERE login = ? AND password = ?";

    private static final String SQL_INSERT_USER =
            "INSERT INTO users (login, password, full_name) VALUES (?, ?, ?)";

    private static UserDAOPostgresImpl instance;

    private UserDAOPostgresImpl() {
    }

    public static UserDAOPostgresImpl getInstance() {
        if (instance == null) {
            synchronized (UserDAOPostgresImpl.class) {
                if (instance == null) {
                    instance = new UserDAOPostgresImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public User getUser(
            Connection connection, String login, String password)
            throws SQLException {

        User user = null;

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_SELECT_USER_BY_LOGIN_AND_PASS)) {

            DAOUtils.fillPreparedStatement(pstmt, login, DigestUtils.md5Hex(password));

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    user = getUser(resultSet, login, password);
                }
            }
        }
        return user;
    }

    @Override
    public boolean setUser(Connection connection, User user)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_INSERT_USER)) {

            DAOUtils.fillPreparedStatement(
                    pstmt,
                    user.getLogin(),
                    DigestUtils.md5Hex(user.getPassword()),
                    user.getFullName());

                return pstmt.executeUpdate() > 0;
        }
    }
}
