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
public class RemoveRouteCommand extends Command {

    private static final Logger LOG = Logger.getLogger(RemoveRouteCommand.class);

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        removeRoute(req, FACADE);

        LOG.debug("Command finished");
        return Paths.CHANGE_ROUTE_SERVLET;
    }

    private void removeRoute(HttpServletRequest req, DAOFacade facade)
            throws AppException {

        int routeId = Integer.parseInt(req.getParameter("routeId"));
        LOG.trace(String.format("Request parameter: routeId --> %d", routeId));
        if(facade.removeRouteById(routeId)){
            LOG.trace("Route successful deleted");
        } else {
            throw new AppException("Failed to remove route");
        }
    }
}
