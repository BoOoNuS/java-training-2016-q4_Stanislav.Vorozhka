package ua.nure.vorozhka.SummaryTask4.web.command.common.authenticating;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.User;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.exception.UserInputException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;
import ua.nure.vorozhka.SummaryTask4.web.validator.IValidator;
import ua.nure.vorozhka.SummaryTask4.web.validator.LoginValidator;
import ua.nure.vorozhka.SummaryTask4.web.validator.PasswordValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Stanislav on 03.01.2017.
 */
public class AuthenticateCommand extends Command {

    private static final Logger LOG = Logger.getLogger(AuthenticateCommand.class);

    private static final IValidator<String> LOGIN_VALIDATOR = LoginValidator.getInstance();

    private static final IValidator<String> PASSWORD_VALIDATOR = PasswordValidator.getInstance();

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        String login = req.getParameter("login");
        LOG.trace(String.format("Request parameter: login --> %s", login));
        String password = req.getParameter("password");
        LOG.trace(String.format("Request parameter: password --> %s", password));

        LOGIN_VALIDATOR.validate(login);
        PASSWORD_VALIDATOR.validate(password);

        User user = FACADE.getUserByLoginAndPassword(login, password);
        setSessionAttributes(req.getSession(), user);

        LOG.debug("Command finished");
        return Paths.AUTHENTICATE_SERVLET;
    }

    private void setSessionAttributes(HttpSession session, User user)
            throws AppException {

        if (user != null) {
            session.setAttribute("user", user);
            LOG.trace(String.format("User --> %s", user));
        } else {
            throw new UserInputException("User with such login and password does not exist");
        }
    }
}
