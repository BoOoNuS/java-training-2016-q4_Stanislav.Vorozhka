package ua.nure.vorozhka.SummaryTask4.web.command.common;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.RouteInfo;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.exception.UserInputException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Stanislav on 12.01.2017.
 */
public class FindCommand extends Command {

    private static final Logger LOG = Logger.getLogger(FindCommand.class);

    private static final DAOFacade FACADE = DAOFacade.getInstance(
            PostgresFactory.getInstance());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        List<RouteInfo> routesInfo = getRoutesInfo(
                req.getParameter("from"), req.getParameter("to"), FACADE);

        checkRoutesInfo(routesInfo);

        SetRoutesInfoToRequest(req, routesInfo);

        LOG.debug("Command finished");
        return Paths.MAIN_PAGE;
    }

    private void checkRoutesInfo(List<RouteInfo> routesInfo)
            throws UserInputException {

        if (routesInfo.isEmpty()) {
            throw new UserInputException("Route between the stations not exist " +
                    "or the stations not exist");
        }
    }

    private List<RouteInfo> getRoutesInfo(
            String fromStationName, String toStationName, DAOFacade facade)
            throws AppException {

        LOG.trace(String.format("Request parameter: from --> %s", fromStationName));
        LOG.trace(String.format("Request parameter: to --> %s", toStationName));

        List<RouteInfo> routesInfo = facade.getRoutesInfoByFromAndToStation(fromStationName, toStationName);

        if (!toStationName.equals(fromStationName)) {
            for (int i = 0; i < routesInfo.size(); i++) {

                if (routesInfo.get(i).getInitialStation() != null &&
                        routesInfo.get(i).getTerminalStation() != null) {

                    String nameOfInitialStation = routesInfo.get(i).getInitialStation().getStationName();
                    String nameOfTerminalStation = routesInfo.get(i).getTerminalStation().getStationName();

                    if (nameOfInitialStation.equals(toStationName) ||
                            nameOfTerminalStation.equals(fromStationName)) {

                        routesInfo.remove(i);
                    }
                } else {
                    throw new AppException("Searching exception, initial or terminal station not set");
                }
            }
        } else {
            throw new AppException("From and To can not be the same");
        }
        return routesInfo;
    }

    private void SetRoutesInfoToRequest(HttpServletRequest req, List<RouteInfo> routesInfo)
            throws UserInputException {

        if (routesInfo != null) {
            LOG.trace(String.format("At user request: RoutesInfo --> %s", routesInfo));
            req.setAttribute("routesInfo", routesInfo);
        } else {
            throw new UserInputException("At your request, no match is found");
        }
    }
}
