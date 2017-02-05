package ua.nure.vorozhka.SummaryTask4.web.command.route.redirecting;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.StationOnRoute;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.exception.DBException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class ToChangeWayStationCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ToChangeWayStationCommand.class);

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        int routeId = Integer.parseInt(req.getParameter("routeId"));

        setStationsOnRouteToRequest(req, FACADE, routeId);

        setRouteIdToRequest(req, routeId);

        LOG.debug("Command finished");
        return Paths.CHANGE_WAY_STATION_PAGE;
    }

    private void setStationsOnRouteToRequest(HttpServletRequest req, DAOFacade facade, int routeId)
            throws DBException {

        List<StationOnRoute> stationsOnRoute = facade.getWayStationsOnRouteByRoute(routeId);
        LOG.trace(String.format("Set to request parameter: stationsOnRoute --> %s", stationsOnRoute.toString()));
        req.setAttribute("stationsOnRoute", stationsOnRoute);
    }

    private void setRouteIdToRequest(HttpServletRequest req, int routeId) {
        LOG.trace(String.format("Request parameter: routeId --> %s", routeId));
        req.setAttribute("routeId", routeId);
    }
}
