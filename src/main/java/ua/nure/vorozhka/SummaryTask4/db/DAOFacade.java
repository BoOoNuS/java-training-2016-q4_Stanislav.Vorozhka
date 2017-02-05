package ua.nure.vorozhka.SummaryTask4.db;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.connector.AbstractFactory;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.RoutesInfoDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.StationDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.TrainPlaceDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.PostgresFactory;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Route;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.*;
import ua.nure.vorozhka.SummaryTask4.exception.DBException;
import ua.nure.vorozhka.SummaryTask4.exception.ExceptionMessages;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stanislav on 18.01.2017.
 */
public final class DAOFacade {

    private static final Logger LOG = Logger.getLogger(PostgresFactory.class);
    private static Map<String, DAOFacade> connectors = new HashMap<>();

    private AbstractFactory factory;

    private DAOFacade(AbstractFactory factory) {
        this.factory = factory;
    }

    public static DAOFacade getInstance(AbstractFactory factory) {
        String fullClassName = factory.getClass().getName();

        if (!connectors.containsKey(fullClassName)) {
            synchronized (DAOFacade.class) {
                if (!connectors.containsKey(fullClassName)) {
                    DAOFacade bl = new DAOFacade(factory);
                    connectors.put(fullClassName, bl);
                    return bl;
                }
            }
        }
        return connectors.get(fullClassName);
    }

    public User getUserByLoginAndPassword(String login, String password)
            throws DBException {

        Connection connection = factory.getConnection();

        try {
            return factory.getUserDAO().getUser(connection, login, password);
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_USER_DOES_NOT_EXIST, e);
            throw new DBException(ExceptionMessages.ERR_USER_DOES_NOT_EXIST, e);
        } finally {
            close(connection);
        }
    }

    public boolean insertUser(User user)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getUserDAO().setUser(connection, user);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(ExceptionMessages.ERR_USER_HAS_NOT_INSERTED, e);
            throw new DBException(ExceptionMessages.ERR_USER_HAS_NOT_INSERTED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public List<Station> getStations()
            throws DBException {

        Connection connection = factory.getConnection();

        try {
            return factory.getStationDAO().getStations(connection);
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_CANNOT_GET_STATIONS, e);
            throw new DBException(ExceptionMessages.ERR_CANNOT_GET_STATIONS, e);
        } finally {
            close(connection);
        }
    }

    public Station getStationByName(String stationName)
            throws DBException {

        Connection connection = factory.getConnection();

        try {
            return factory.getStationDAO().getStationByName(connection, stationName);
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_CANNOT_GET_STATIONS, e);
            throw new DBException(ExceptionMessages.ERR_CANNOT_GET_STATIONS, e);
        } finally {
            close(connection);
        }
    }

    public Station getStationByRouteIdAndStationTypeId(int routeId, int stationId)
            throws DBException {

        Connection connection = factory.getConnection();

        try {
            return factory.getStationDAO().
                    getStationByRouteIdAndStationTypeId(
                            connection, routeId, stationId);

        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_CANNOT_GET_STATIONS, e);
            throw new DBException(ExceptionMessages.ERR_CANNOT_GET_STATIONS, e);
        } finally {
            close(connection);
        }
    }

    public boolean insertStation(Station station)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getStationDAO().setStation(connection, station);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(ExceptionMessages.ERR_STATION_HAS_NOT_INSERTED, e);
            throw new DBException(ExceptionMessages.ERR_STATION_HAS_NOT_INSERTED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public boolean removeStationById(int stationId)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getStationDAO().removeStationById(connection, stationId);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(ExceptionMessages.ERR_STATION_HAS_NOT_REMOVED, e);
            throw new DBException(ExceptionMessages.ERR_STATION_HAS_NOT_REMOVED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public boolean updateStation(Station station)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getStationDAO().updateStation(connection, station);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(ExceptionMessages.ERR_STATION_HAS_NOT_UPDATED, e);
            throw new DBException(ExceptionMessages.ERR_STATION_HAS_NOT_UPDATED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public boolean decrementCountOfSeats(TrainPlace trainPlace)
            throws DBException {

        Connection connection = factory.getConnection();
        TrainPlaceDAO trainPlaceDAO = factory.getTrainPlaceDAO();
        boolean result = false;

        try {
            int placeTypeId = trainPlace.getType().ordinal() + 1;

            TrainPlace currentTrainPlace = trainPlaceDAO.
                    getTrainPlaceByTrainNumAndPlaceTypeId(
                            connection, trainPlace.getTrainNum(), placeTypeId);

            if (currentTrainPlace.getFreePlaces() > 0) {
                result = trainPlaceDAO.updateTrainPlace(connection, currentTrainPlace);
            }

            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(ExceptionMessages.ERR_FAILED_TO_DECREMENT_FREE_SEATS, e);
            throw new DBException(ExceptionMessages.ERR_FAILED_TO_DECREMENT_FREE_SEATS, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public List<RouteInfo> getRoutesInfoByFromAndToStation(
            String fromStationName, String toStationName)
            throws DBException {

        Connection connection = factory.getConnection();
        StationDAO stationDAO = factory.getStationDAO();

        try {
            Station fromStations = stationDAO.getStationByName(connection, fromStationName);
            Station toStations = stationDAO.getStationByName(connection, toStationName);

            return factory.getRoutesInfoDAO().templateMethod(connection, fromStations, toStations);
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_SELECTING_DATABASE_ERROR_OCCURRED, e);
            throw new DBException(ExceptionMessages.ERR_SELECTING_DATABASE_ERROR_OCCURRED, e);
        } finally {
            close(connection);
        }
    }

    public List<RouteInfo> getAllTrainsOnRoute()
            throws DBException {

        Connection connection = factory.getConnection();
        RoutesInfoDAO routesInfoDAO = factory.getRoutesInfoDAO();
        List<RouteInfo> routesInfo;

        try {
            routesInfo = routesInfoDAO.getAllTrainsOnRoute(connection);
            routesInfoDAO.finishRouteInfo(connection, routesInfo);
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_SELECTING_DATABASE_ERROR_OCCURRED, e);
            throw new DBException(ExceptionMessages.ERR_SELECTING_DATABASE_ERROR_OCCURRED, e);
        } finally {
            close(connection);
        }
        return routesInfo;
    }

    public boolean insertStationOnRoute(StationOnRoute stationOnRoute)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getStationOnRouteDAO().setStationOnRoute(connection, stationOnRoute);
            connection.commit();
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_STATION_ON_ROUTE_HAS_NOT_INSERTED, e);
            throw new DBException(ExceptionMessages.ERR_STATION_ON_ROUTE_HAS_NOT_INSERTED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public boolean removeStationOnRouteByRouteIdAndStationId(
            int routeId, int stationId)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getStationOnRouteDAO().
                    removeStationOnRouteByRouteIdAndStationId(
                            connection, routeId, stationId);

            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(ExceptionMessages.ERR_STATION_ON_ROUTE_HAS_NOT_REMOVED, e);
            throw new DBException(ExceptionMessages.ERR_STATION_ON_ROUTE_HAS_NOT_REMOVED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public boolean updateStationOnRoute(StationOnRoute stationOnRoute)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getStationOnRouteDAO().updateStationOnRoute(connection, stationOnRoute);
            connection.commit();
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_STATION_ON_ROUTE_HAS_NOT_UPDATED, e);
            throw new DBException(ExceptionMessages.ERR_STATION_ON_ROUTE_HAS_NOT_UPDATED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public List<StationOnRoute> getWayStationsOnRouteByRoute(int routeId)
            throws DBException {

        Connection connection = factory.getConnection();

        try {
            return factory.getStationOnRouteDAO().getWayStationsOnRouteByRoute(connection, routeId);
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_CANNOT_GET_STATION_ON_ROUTE, e);
            throw new DBException(ExceptionMessages.ERR_CANNOT_GET_STATION_ON_ROUTE, e);
        } finally {
            close(connection);
        }
    }

    public boolean removeRouteById(int routeId)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getRouteDAO().removeRouteById(connection, routeId);
            connection.commit();
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_ROUTE_HAS_NOT_REMOVED, e);
            throw new DBException(ExceptionMessages.ERR_ROUTE_HAS_NOT_REMOVED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public boolean insertRouteAndTrainOnIt(Train train, Route route)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getRouteDAO().setRoute(connection, route);

            if (route.getId() > 0) {
                train.setRouteId(route.getId());
                result &= factory.getTrainDAO().updateTrain(connection, train);
            } else {
                throw new DBException(ExceptionMessages.ERR_ROUTE_HAS_NOT_INSERTED);
            }

            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(ExceptionMessages.ERR_TRAIN_HAS_NOT_INSERTED, e);
            throw new DBException(ExceptionMessages.ERR_TRAIN_HAS_NOT_INSERTED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public boolean updateRoute(Route route)
            throws DBException {

        Connection connection = factory.getConnection();
        boolean result;

        try {
            result = factory.getRouteDAO().updateRoute(connection, route);
            connection.commit();
        } catch (SQLException e) {
            rollback(connection);
            LOG.error(ExceptionMessages.ERR_ROUTE_HAS_NOT_UPDATED, e);
            throw new DBException(ExceptionMessages.ERR_ROUTE_HAS_NOT_UPDATED, e);
        } finally {
            close(connection);
        }
        return result;
    }

    public List<Train> getAllTrains()
            throws DBException {

        Connection connection = factory.getConnection();

        try {
            return factory.getTrainDAO().getTrains(connection);
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_CANNOT_GET_TRAINS, e);
            throw new DBException(ExceptionMessages.ERR_CANNOT_GET_TRAINS, e);
        } finally {
            close(connection);
        }
    }

    private void rollback(Connection connection)
            throws DBException {

        try {
            connection.rollback();
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_CANNOT_ROLL_BACK, e);
            throw new DBException(ExceptionMessages.ERR_CANNOT_ROLL_BACK, e);
        }
    }

    private void close(Connection connection)
            throws DBException {

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(ExceptionMessages.ERR_FAILED_TO_CLOSE_CONNECTION, e);
                throw new DBException(ExceptionMessages.ERR_FAILED_TO_CLOSE_CONNECTION, e);
            }
        }
    }
}
