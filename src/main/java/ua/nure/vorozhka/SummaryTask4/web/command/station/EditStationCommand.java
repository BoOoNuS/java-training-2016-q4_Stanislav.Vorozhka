package ua.nure.vorozhka.SummaryTask4.web.command.station;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;
import ua.nure.vorozhka.SummaryTask4.web.validator.IValidator;
import ua.nure.vorozhka.SummaryTask4.web.validator.StationNameValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 12.01.2017.
 */
public class EditStationCommand extends Command {

    private static final Logger LOG = Logger.getLogger(EditStationCommand.class);

    private static final IValidator<String> STATION_NAME_VALIDATOR =
            StationNameValidator.getInstance();

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        Station station = getStation(
                req.getParameter("stationId"), req.getParameter("stationName"));

        updateStation(FACADE, station);

        LOG.debug("Command finished");
        return Paths.CHANGE_STATION_SERVLET;
    }

    private void updateStation(DAOFacade facade, Station station)
            throws AppException {

        if(facade.updateStation(station)){
            LOG.trace("Station successful updated");
        } else {
            throw new AppException("Failed to update the station");
        }
    }

    private Station getStation(String stationId, String stationName)
            throws ValidateException {

        LOG.trace(String.format("Request parameter: stationId --> %s", stationId));
        LOG.trace(String.format("Request parameter: stationName --> %s", stationName));

        STATION_NAME_VALIDATOR.validate(stationName);

        Station station = new Station();
        station.setId(Integer.parseInt(stationId));
        station.setName(stationName);
        return station;
    }
}
