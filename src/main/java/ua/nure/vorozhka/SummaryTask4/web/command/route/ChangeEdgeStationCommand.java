package ua.nure.vorozhka.SummaryTask4.web.command.route;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;
import ua.nure.vorozhka.SummaryTask4.db.model.constant.StationType;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.StationOnRoute;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.exception.DBException;
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
 * Created by Stanislav on 20.01.2017.
 */
public class ChangeEdgeStationCommand extends Command {

    private static final Logger LOG = Logger.getLogger(ChangeEdgeStationCommand.class);

    private static final IValidator<String> DATE_VALIDATOR = DateValidator.getInstance();

    private static final IValidator<String> TIME_VALIDATOR = TimeValidator.getInstance();

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        int stationId = Integer.parseInt(req.getParameter("stationId"));
        LOG.trace(String.format("Request parameter: stationId --> %d", stationId));

        StationOnRoute stationOnRoute = getStationOnRoute(req, FACADE);

        strategyMethod(FACADE, stationId, stationOnRoute);

        LOG.debug("Command finished");
        return Paths.CHANGE_ROUTE_SERVLET;
    }

    private StationOnRoute getStationOnRoute(HttpServletRequest req, DAOFacade facade)
            throws AppException {

        int routeId = Integer.parseInt(req.getParameter("routeId"));
        LOG.trace(String.format("Request parameter: routeId --> %d", routeId));

        int stationTypeId = Integer.parseInt(req.getParameter("stationTypeId"));
        LOG.trace(String.format("Request parameter: stationTypeId --> %d", stationTypeId));

        String updatedStationName = req.getParameter("updatedStationName");
        LOG.trace(String.format("Request parameter: updatedStationName --> %s", updatedStationName));

        String strDate = req.getParameter("date");
        DATE_VALIDATOR.validate(strDate);
        Date date = Date.valueOf(strDate);
        LOG.trace(String.format("Request parameter: date --> %s", date.toString()));

        String strTime = String.format("%s:00", req.getParameter("time"));
        TIME_VALIDATOR.validate(strTime);
        Time time = Time.valueOf(strTime);
        LOG.trace(String.format("Request parameter: time --> %s", strTime));

        StationOnRoute stationOnRoute = new StationOnRoute();
        Station updatedStation = facade.getStationByName(updatedStationName);

        stationOnRoute.setStation(updatedStation);
        stationOnRoute.setRouteId(routeId);
        stationOnRoute.setType(StationType.getStationType(stationTypeId));
        stationOnRoute.setDate(date);
        stationOnRoute.setTime(String.valueOf(time.getTime()));

        return stationOnRoute;
    }

    private void strategyMethod(
            DAOFacade facade, int stationId, StationOnRoute stationOnRoute)
            throws DBException {

        if(stationId == 0){
            LOG.trace("StationOnRoute insert started");
            facade.insertStationOnRoute(stationOnRoute);
        } else {
            LOG.trace("StationOnRoute update started");
            facade.updateStationOnRoute(stationOnRoute);
        }
    }
}
