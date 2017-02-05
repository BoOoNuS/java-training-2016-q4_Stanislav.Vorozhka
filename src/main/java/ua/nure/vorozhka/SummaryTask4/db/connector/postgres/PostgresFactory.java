package ua.nure.vorozhka.SummaryTask4.db.connector.postgres;

import org.apache.log4j.Logger;
import ua.nure.vorozhka.SummaryTask4.db.connector.AbstractFactory;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.*;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.RouteDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.StationDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.StationOnRouteDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.TrainDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.TrainPlaceDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.UserDAO;
import ua.nure.vorozhka.SummaryTask4.db.connector.postgres.dao.*;
import ua.nure.vorozhka.SummaryTask4.exception.DBException;
import ua.nure.vorozhka.SummaryTask4.exception.ExceptionMessages;
import ua.nure.vorozhka.SummaryTask4.exception.ExceptionWhenDBStarts;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Stanislav on 03.01.2017.
 */
public final class PostgresFactory implements AbstractFactory {

    private static final Logger LOG = Logger.getLogger(PostgresFactory.class);
    private static PostgresFactory instance;
    private static DataSource dataSource;

    private PostgresFactory() {
    }

    public static PostgresFactory getInstance()
            throws ExceptionWhenDBStarts {

        if (instance == null) {
            synchronized (PostgresFactory.class) {
                if (instance == null) {
                    instance = new PostgresFactory();
                    try {
                        Context initContext = new InitialContext();
                        Context envContext = (Context) initContext.lookup("java:/comp/env");
                        dataSource = (DataSource) envContext.lookup("jdbc/ST4DB");
                        LOG.trace("Data source --> " + dataSource);
                    } catch (NamingException e) {
                        LOG.error(ExceptionMessages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
                        throw new ExceptionWhenDBStarts(ExceptionMessages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
                    }
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection()
            throws DBException {

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error(ExceptionMessages.ERR_CANNOT_OBTAIN_CONNECTION, e);
            throw new DBException(ExceptionMessages.ERR_CANNOT_OBTAIN_CONNECTION, e);
        }
    }

    @Override
    public UserDAO getUserDAO() {
        return UserDAOPostgresImpl.getInstance();
    }

    @Override
    public StationDAO getStationDAO() {
        return StationDAOPostgresImpl.getInstance();
    }

    @Override
    public RoutesInfoDAO getRoutesInfoDAO() {
        return RouteInfoDAOPostgresImpl.getInstance();
    }

    @Override
    public TrainPlaceDAO getTrainPlaceDAO() {
        return TrainPlaceDAOPostgresImpl.getInstance();
    }

    @Override
    public StationOnRouteDAO getStationOnRouteDAO() {
        return StationOnRouteDAOPostgresImpl.getInstance();
    }

    @Override
    public RouteDAO getRouteDAO() {
        return RouteDAOPostgresImpl.getInstance();
    }

    @Override
    public TrainDAO getTrainDAO() {
        return TrainDAOPostgresImpl.getInstance();
    }
}
