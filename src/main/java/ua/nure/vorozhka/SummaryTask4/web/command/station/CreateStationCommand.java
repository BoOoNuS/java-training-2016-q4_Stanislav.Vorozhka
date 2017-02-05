package ua.nure.vorozhka.SummaryTask4.web.command.station;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.exception.DBException;
import ua.nure.vorozhka.SummaryTask4.exception.UserInputException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;
import ua.nure.vorozhka.SummaryTask4.web.validator.IValidator;
import ua.nure.vorozhka.SummaryTask4.web.validator.StationNameValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stanislav on 12.01.2017.
 */
public class CreateStationCommand extends Command {

    private static final Logger LOG = Logger.getLogger(CreateStationCommand.class);

    private static final IValidator<String> STATION_NAME_VALIDATOR =
            StationNameValidator.getInstance();

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        String stationName = req.getParameter("stationName");
        Station station = getStation(stationName);

        STATION_NAME_VALIDATOR.validate(stationName);
        insertUser(FACADE, station);

        LOG.debug("Command finished");
        return Paths.CHANGE_STATION_SERVLET;
    }

    private void insertUser(DAOFacade facade, Station station)
            throws DBException, UserInputException {

        if(facade.insertStation(station)){
            LOG.trace("Station successfully added");
        } else {
            throw new UserInputException("Incorrect date input");
        }
    }

    private Station getStation(String stationName){
        LOG.trace(String.format("Request parameter: stationName --> %s", stationName));
        Station station = new Station();
        station.setName(stationName);
        return station;
    }
}
