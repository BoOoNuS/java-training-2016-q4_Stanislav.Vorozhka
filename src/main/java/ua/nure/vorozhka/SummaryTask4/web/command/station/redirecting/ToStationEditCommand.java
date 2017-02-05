package ua.nure.vorozhka.SummaryTask4.web.command.station.redirecting;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 12.01.2017.
 */
public class ToStationEditCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ToStationEditCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("Command starts");

        String stationId = req.getParameter("stationId");
        LOG.trace(String.format("Request parameter: stationId --> %s", stationId));

        req.setAttribute("stationId", stationId);

        LOG.debug("Command finished");
        return Paths.EDIT_STATION_PAGE;
    }
}
