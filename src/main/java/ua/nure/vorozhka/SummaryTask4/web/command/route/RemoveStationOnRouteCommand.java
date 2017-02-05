package ua.nure.vorozhka.SummaryTask4.web.command.route;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class RemoveStationOnRouteCommand extends Command {

    private static final Logger LOG = Logger.getLogger(RemoveStationOnRouteCommand.class);

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        int routeId = getRouteId(req);

        int stationId = getStationId(req);

        removeStationOnRoute(FACADE, routeId, stationId);

        LOG.debug("Command finished");
        return Paths.CHANGE_ROUTE_SERVLET;
    }

    private void removeStationOnRoute(DAOFacade facade, int routeId, int stationId)
            throws AppException {

        if(facade.removeStationOnRouteByRouteIdAndStationId(routeId, stationId)) {
            LOG.trace("StationOnRoute successful removed");
        } else {
            throw new AppException("Failed to remove station on route");
        }
    }

    private int getStationId(HttpServletRequest req) {
        int stationId = Integer.parseInt(req.getParameter("stationId"));
        LOG.trace(String.format("Request parameter: stationId --> %d", stationId));
        return stationId;
    }

    private int getRouteId(HttpServletRequest req) {
        int routeId = Integer.parseInt(req.getParameter("routeId"));
        LOG.trace(String.format("Request parameter: routeId --> %d", routeId));
        return routeId;
    }
}
