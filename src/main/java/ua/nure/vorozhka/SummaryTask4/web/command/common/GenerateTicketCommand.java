package ua.nure.vorozhka.SummaryTask4.web.command.common;

import com.itextpdf.text.DocumentException;
import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.DAOFacade;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.RouteInfo;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.TrainPlace;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.User;
import ua.nure.vorozhka.SummaryTask4.exception.AppException;
import ua.nure.vorozhka.SummaryTask4.web.Paths;
import ua.nure.vorozhka.SummaryTask4.web.command.Command;
import ua.nure.vorozhka.SummaryTask4.web.parser.IParser;
import ua.nure.vorozhka.SummaryTask4.web.parser.RouteInfoParser;
import ua.nure.vorozhka.SummaryTask4.web.parser.TrainPlaceParser;
import ua.nure.vorozhka.SummaryTask4.web.report.TicketGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Stanislav on 13.01.2017.
 */
public class GenerateTicketCommand extends Command {

    private static final Logger LOG = Logger.getLogger(GenerateTicketCommand.class);

    private static final IParser<RouteInfo> ROUTE_INFO_PARSER = RouteInfoParser.getInstance();

    private static final IParser<TrainPlace> TRAIN_PLACE_PARSER = TrainPlaceParser.getInstance();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)
            throws AppException {

        LOG.debug("Command starts");

        HttpSession session = req.getSession();

        RouteInfo routeInfo = getRouteInfo(req.getParameter("routeInfo"));
        TrainPlace trainPlace = getTrainPlace(req.getParameter("trainPlace"));

        User user = getUser(session);

        String realPath = req.getServletContext().getRealPath("WEB-INF/resources/");

        createTicket(routeInfo, trainPlace, user, realPath);

        setForwardAddressToSession(session, user);

        LOG.debug("Command finished");
        return Paths.TICKET_SERVLET;
    }

    private TrainPlace getTrainPlace(String trainPlaceStr) {
        TrainPlace trainPlace = TRAIN_PLACE_PARSER.parse(trainPlaceStr);
        LOG.trace(String.format("Request parameter: trainPlace --> %s", trainPlace));
        return trainPlace;
    }

    private RouteInfo getRouteInfo(String routeInfoStr) {
        RouteInfo routeInfo = ROUTE_INFO_PARSER.parse(routeInfoStr);
        LOG.trace(String.format("Request parameter: routeInfo --> %s", routeInfo));
        return routeInfo;
    }

    private void setForwardAddressToSession(HttpSession session, User user) {
        String forward = String.format("%s%s.pdf", Paths.PDF_REPORTS_PATH, user.getLogin());
        session.setAttribute("ticketAddress", forward);
    }

    private void createTicket(
            RouteInfo routeInfo, TrainPlace trainPlace, User user, String realPath)
            throws AppException {

        DAOFacade facade = DAOFacade.getInstance(PostgresFactory.getInstance());

        try {

            if (facade.decrementCountOfSeats(trainPlace)) {
                TicketGenerator.createTicket(user, routeInfo, trainPlace, realPath);
                LOG.trace("Ticket successful generated");
            } else {
                throw new AppException("This train has no seats");
            }

        } catch (IOException | DocumentException e) {
            throw new AppException("Error when trying to create a ticket");
        }
    }

    private User getUser(HttpSession session)
            throws AppException {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new AppException("You are not authenticated, " +
                    "only authenticated users can buy ticket");
        }
        LOG.trace(String.format("Session attribute: user --> %s", user.toString()));
        return user;
    }
}
