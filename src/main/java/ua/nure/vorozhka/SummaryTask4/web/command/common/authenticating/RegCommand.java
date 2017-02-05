package ua.nure.vorozhka.SummaryTask4.web.command.common.authenticating;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.User;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.exception.DBException;
import ua.nure.vorozhka.SummaryTask4.exception.UserInputException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;
import ua.nure.vorozhka.SummaryTask4.web.validator.IValidator;
import ua.nure.vorozhka.SummaryTask4.web.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 03.01.2017.
 */
public class RegCommand extends Command {

    private static final Logger LOG = Logger.getLogger(RegCommand.class);

    private static final IValidator<User> USER_VALIDATOR = UserValidator.getInstance();

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        User user = getUser(req);

        USER_VALIDATOR.validate(user);

        insertUser(FACADE, user);

        LOG.debug("Command finished");
        return Paths.REG_SERVLET;
    }

    private void insertUser(DAOFacade facade, User user)
            throws DBException, UserInputException {

        if (facade.insertUser(user)) {
            LOG.trace("User successfully added");
        } else {
            throw new UserInputException("Incorrect date input");
        }
    }

    private User getUser(HttpServletRequest req) {
        String login = req.getParameter("login");
        LOG.trace(String.format("Request parameter: login --> %s", login));
        String password = req.getParameter("password");
        LOG.trace(String.format("Request parameter: password --> %s", password));
        String fullName = req.getParameter("fullName");
        LOG.trace(String.format("Request parameter: full name --> %s", fullName));

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setFullName(fullName);

        return user;
    }
}
