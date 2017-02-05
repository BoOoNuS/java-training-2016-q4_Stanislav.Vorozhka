package ua.nure.vorozhka.SummaryTask4.web.command.route.redirecting;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class ToStationOnRouteRemove extends Command {

    private static final Logger LOG = Logger.getLogger(ToStationOnRouteRemove.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        LOG.debug("Command starts");

        setRouteIdToRequest(req);

        setStationIdToRequest(req);

        LOG.debug("Command finished");
        return Paths.REMOVE_STATION_ON_ROUTE_CONFIRMATION_PAGE;
    }

    private void setStationIdToRequest(HttpServletRequest req) {
        String stationId = req.getParameter("stationId");
        LOG.trace(String.format("Request parameter: stationId --> %s", stationId));
        req.setAttribute("stationId", stationId);
    }

    private void setRouteIdToRequest(HttpServletRequest req) {
        String routeId = req.getParameter("routeId");
        LOG.trace(String.format("Request parameter: routeId --> %s", routeId));
        req.setAttribute("routeId", routeId);
    }
}
