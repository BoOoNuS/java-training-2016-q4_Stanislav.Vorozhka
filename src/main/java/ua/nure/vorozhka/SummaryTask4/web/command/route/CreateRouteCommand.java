package ua.nure.vorozhka.SummaryTask4.web.command.route;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Route;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.Train;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class CreateRouteCommand extends Command {

    private static final Logger LOG = Logger.getLogger(CreateRouteCommand.class);

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        Route route = getRoute(req);

        Train train = getTrain(req, route.getId());

        insertRouteAndTrainOnIt(FACADE, train, route);

        LOG.debug("Command finished");
        return Paths.CHANGE_ROUTE_SERVLET;
    }

    private void insertRouteAndTrainOnIt(
            DAOFacade facade, Train train, Route route)
            throws AppException {

        if (facade.insertRouteAndTrainOnIt(train, route)) {
            LOG.trace("Route and train on it, successful inserted");
        } else {
            throw new AppException("Incorrect data input");
        }
    }

    private Train getTrain(HttpServletRequest req, int routeId) {
        int trainNum = Integer.parseInt(req.getParameter("trainNum"));
        LOG.trace(String.format("Request parameter: trainNum --> %d", trainNum));
        Train train = new Train();
        train.setNumber(trainNum);
        train.setRouteId(routeId);
        return train;
    }

    private Route getRoute(HttpServletRequest req) {
        String travelTimeStr = String.format("%s:00", req.getParameter("travelTime"));
        Time travelTime = Time.valueOf(travelTimeStr);

        LOG.trace(String.format("Request parameter: travelTime --> %s", travelTime.toString()));

        Route route = new Route();
        route.setTravelTime(travelTime);

        return route;
    }
}
