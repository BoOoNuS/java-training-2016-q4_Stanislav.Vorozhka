package ua.nure.vorozhka.SummaryTask4.web.command.route.redirecting;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.exception.DBException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Stanislav on 20.01.2017.
 */
public class ToChangeEdgeStationCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ToChangeEdgeStationCommand.class);

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        int routeId = Integer.parseInt(req.getParameter("routeId"));
        int stationTypeId = Integer.parseInt(req.getParameter("stationTypeId"));

        setRouteIdToRequest(req, routeId);
        setStationTypeIdToRequest(req, stationTypeId);
        setStationsToRequest(req, FACADE);
        setCurrentStationToRequest(req, FACADE, routeId, stationTypeId);

        LOG.debug("Command finished");
        return Paths.CHANGE_EDGE_STATION_PAGE;
    }

    private void setCurrentStationToRequest(
            HttpServletRequest req, DAOFacade facade, int routeId, int stationId)
            throws DBException {

        Station currentStation = facade.getStationByRouteIdAndStationTypeId(routeId, stationId);

        LOG.trace(String.format(
                "Set to request parameter attribute: currentStation --> %s",
                currentStation.toString()));

        req.setAttribute("currentStation", currentStation);
    }

    private void setRouteIdToRequest(HttpServletRequest req, int routeId) {
        LOG.trace(String.format("Request parameter: routeId --> %d", routeId));
        req.setAttribute("routeId", routeId);
    }

    private void setStationTypeIdToRequest(HttpServletRequest req, int stationTypeId) {
        LOG.trace(String.format("Request parameter: stationTypeId --> %d", stationTypeId));
        req.setAttribute("stationTypeId", stationTypeId);
    }

    private void setStationsToRequest(HttpServletRequest req, DAOFacade facade)
            throws DBException {

        List<Station> stations = facade.getStations();

        LOG.trace(String.format(
                "Set to request parameter attribute: stations --> %s",
                stations.toString()));

        req.setAttribute("stations", stations);
    }
}
