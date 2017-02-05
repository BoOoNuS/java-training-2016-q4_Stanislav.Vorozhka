package ua.nure.vorozhka.SummaryTask4.web.command.station;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 12.01.2017.
 */
public class RemoveStationCommand extends Command {

    private static final Logger LOG = Logger.getLogger(RemoveStationCommand.class);

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        int stationId = Integer.parseInt(req.getParameter("stationId"));
        LOG.trace(String.format("Request parameter: stationId --> %d", stationId));

        removeStation(FACADE, stationId);

        LOG.debug("Command finished");
        return Paths.CHANGE_STATION_SERVLET;
    }

    private void removeStation(DAOFacade facade, int stationId)
            throws AppException {

        if(facade.removeStationById(stationId)){
            LOG.trace("Station successful deleted");
        } else {
            throw new AppException("Failed to delete the station");
        }
    }
}
