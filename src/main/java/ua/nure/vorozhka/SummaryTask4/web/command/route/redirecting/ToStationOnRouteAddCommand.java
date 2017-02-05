package ua.nure.vorozhka.SummaryTask4.web.command.route.redirecting;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class ToStationOnRouteAddCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ToStationOnRouteAddCommand.class);

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command started");

        setStationsToRequest(req, FACADE.getStations());
        setRouteIdToRequest(req);

        LOG.debug("Command started");
        return Paths.ADD_STATION_TO_ROUTE_PAGE;
    }

    private void setStationsToRequest(HttpServletRequest req, List<Station> stations)
            throws AppException {

        LOG.trace(String.format(
                "Set to request parameter: stations --> %s", stations.toString()));

        req.setAttribute("stations", stations);
    }

    private void setRouteIdToRequest(HttpServletRequest req) {
        String routeId = req.getParameter("routeId");
        LOG.trace(String.format("Request parameter: routeId --> %s", routeId));
        req.setAttribute("routeId", routeId);
    }
}
