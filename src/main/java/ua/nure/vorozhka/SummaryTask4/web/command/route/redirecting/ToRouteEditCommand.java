package ua.nure.vorozhka.SummaryTask4.web.command.route.redirecting;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.Train;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class ToRouteEditCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ToRouteEditCommand.class);

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        setToRequestTrains(req, FACADE.getAllTrains());
        setToRequestRouteId(req, req.getParameter("routeId"));

        LOG.debug("Command finished");
        return Paths.EDIT_ROUTE_PAGE;
    }

    private void setToRequestTrains(HttpServletRequest req, List<Train> trains){
        LOG.debug(String.format("Set to request: trains --> %s", trains));
        req.setAttribute("trains", trains);
    }

    private void setToRequestRouteId(HttpServletRequest req, String routeId){
        LOG.trace(String.format("Set to request: routeId --> %s", routeId));
        req.setAttribute("routeId", routeId);
    }
}
