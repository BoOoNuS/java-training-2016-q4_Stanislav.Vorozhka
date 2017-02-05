package ua.nure.vorozhka.SummaryTask4.web.command.common;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 12.01.2017.
 */
public class ToMainCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ToMainCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)  {
        LOG.debug("Command starts");
        // no op
        LOG.debug("Command finished");
        return Paths.MAIN_PAGE;
    }
}
