package ua.nure.vorozhka.SummaryTask4.web.command.route;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Route;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.Train;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;
import ua.nure.vorozhka.SummaryTask4.web.validator.IValidator;
import ua.nure.vorozhka.SummaryTask4.web.validator.TimeValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class EditRouteCommand extends Command {

    private static final Logger LOG = Logger.getLogger(EditRouteCommand.class);

    private static final IValidator<String> TIME_VALIDATOR = TimeValidator.getInstance();

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        int routeId = getRouteId(req);

        Route route = getRoute(req, routeId);

        updateRoute(FACADE, route);

        LOG.debug("Command finished");
        return Paths.CHANGE_ROUTE_SERVLET;
    }

    private int getRouteId(HttpServletRequest req) {
        int routeId = Integer.parseInt(req.getParameter("routeId"));
        LOG.trace(String.format("Request parameter: routeId --> %d", routeId));
        return routeId;
    }

    private void updateRoute(DAOFacade facade, Route route)
            throws AppException {

        if (facade.updateRoute(route)) {
            LOG.trace("Route and train on it, successful updated");
        } else {
            throw new AppException("Incorrect data input");
        }
    }

    private Route getRoute(HttpServletRequest req, int routeId)
            throws AppException {

        String strTravelTime = String.format("%s:00", req.getParameter("travelTime"));
        TIME_VALIDATOR.validate(strTravelTime);
        Time travelTime = Time.valueOf(strTravelTime);
        LOG.trace(String.format("Request parameter: travelTime --> %s", strTravelTime));

        Route route = new Route();
        route.setTravelTime(travelTime);
        route.setId(routeId);

        return route;
    }
}
