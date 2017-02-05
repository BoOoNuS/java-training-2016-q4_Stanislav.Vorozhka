package ua.nure.vorozhka.SummaryTask4.db.connector;

import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.*;
import ua.nure.vorozhka.SummaryTask4.exception.DBException;

import java.sql.Connection;

/**
 * Created by Stanislav on 18.01.2017.
 */
public interface AbstractFactory {

    Connection getConnection() throws DBException;
    UserDAO getUserDAO();
    StationDAO getStationDAO();
    RoutesInfoDAO getRoutesInfoDAO();
    TrainPlaceDAO getTrainPlaceDAO();
    StationOnRouteDAO getStationOnRouteDAO();
    RouteDAO getRouteDAO();
    TrainDAO getTrainDAO();
}
