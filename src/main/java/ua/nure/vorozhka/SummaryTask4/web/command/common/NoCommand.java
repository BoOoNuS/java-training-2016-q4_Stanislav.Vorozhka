package ua.nure.vorozhka.SummaryTask4.web.command.common;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 03.01.2017.
 */
public class NoCommand extends Command {

    private static final Logger LOG = Logger.getLogger(NoCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("Command starts");

        String errorMessage = "No such command";
        req.getSession().setAttribute("error", errorMessage);
        LOG.error(String.format("Set the request attribute: errorMessage --> %s", errorMessage));

        LOG.debug("Command finished");
        return Paths.ERROR_SERVLET;
    }
}
