package ua.nure.vorozhka.SummaryTask4.web.command.route;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.constant.StationType;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.StationOnRoute;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;
import ua.nure.vorozhka.SummaryTask4.web.validator.DateValidator;
import ua.nure.vorozhka.SummaryTask4.web.validator.IValidator;
import ua.nure.vorozhka.SummaryTask4.web.validator.TimeValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class AddStationToRouteCommand extends Command {

    private static final Logger LOG = Logger.getLogger(AddStationToRouteCommand.class);

    private static final IValidator<String> DATE_VALIDATOR = DateValidator.getInstance();

    private static final IValidator<String> TIME_VALIDATOR = TimeValidator.getInstance();

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        String time = getTime(req);

        StationOnRoute stationOnRoute = getStationOnRoute(req, FACADE, time);

        FACADE.insertStationOnRoute(stationOnRoute);

        LOG.debug("Command finished");
        return Paths.CHANGE_ROUTE_SERVLET;
    }

    private StationOnRoute getStationOnRoute(HttpServletRequest req, DAOFacade facade, String time)
            throws AppException {

        int routeId = Integer.parseInt(req.getParameter("routeId"));
        LOG.trace(String.format("Request parameter: routeId --> %d", routeId));

        int typeId = Integer.parseInt(req.getParameter("typeId"));
        LOG.trace(String.format("Request parameter: typeId --> %d", typeId));

        String stationName = req.getParameter("stationName");
        LOG.trace(String.format("Request parameter: stationName --> %s", stationName));

        String strDate = req.getParameter("date");
        DATE_VALIDATOR.validate(strDate);
        Date date = Date.valueOf(strDate);
        LOG.trace(String.format("Request parameter: date --> %s", strDate));

        StationOnRoute stationOnRoute = new StationOnRoute();

        stationOnRoute.setRouteId(routeId);
        stationOnRoute.setStation(facade.getStationByName(stationName));
        stationOnRoute.setType(StationType.getStationType(typeId));
        stationOnRoute.setDate(date);
        stationOnRoute.setTime(time.toString());
        return stationOnRoute;
    }

    private String getTime(HttpServletRequest req)
            throws AppException {

        String strArrivalTime = String.format("%s:00", req.getParameter("arrivalTime"));
        TIME_VALIDATOR.validate(strArrivalTime);
        Time arrivalTime = Time.valueOf(strArrivalTime);
        LOG.trace(String.format("Request parameter: arrivalTime --> %s", strArrivalTime));

        String strParkingTime = String.format("%s:00", req.getParameter("parkingTime"));
        TIME_VALIDATOR.validate(strParkingTime);
        Time parkingTime = Time.valueOf(strParkingTime);
        LOG.trace(String.format("Request parameter: parkingTime --> %s", strParkingTime));

        String strDepartureTime = String.format("%s:00", req.getParameter("departureTime"));
        TIME_VALIDATOR.validate(strDepartureTime);
        Time departureTime = Time.valueOf(strDepartureTime);
        LOG.trace(String.format("Request parameter: departureTime --> %s", strDepartureTime));

        StringBuilder sb = new StringBuilder();
        sb.append(arrivalTime.getTime()).append("/")
                .append(parkingTime.getTime()).append("/")
                .append(departureTime.getTime());

        return sb.toString();
    }
}
