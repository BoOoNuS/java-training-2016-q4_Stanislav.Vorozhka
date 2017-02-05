package ua.nure.vorozhka.SummaryTask4.web.command.common.authenticating;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Stanislav on 03.01.2017.
 */
public class LogoutCommand extends Command {

    private static final Logger LOG = Logger.getLogger(AuthenticateCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("Command starts");

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        LOG.debug("Command finished");
        return Paths.LOGIN_PAGE;
    }
}
