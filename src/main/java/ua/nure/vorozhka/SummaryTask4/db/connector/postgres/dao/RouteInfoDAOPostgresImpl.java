package ua.nure.vorozhka.SummaryTask4.db.connector.postgres.dao;

import ua.nure.vorozhka.SummaryTask4.db.connector.DAOUtils;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.RoutesInfoDAO;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.RouteInfo;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.TrainPlace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 12.01.2017.
 */
public final class RouteInfoDAOPostgresImpl extends RoutesInfoDAO {

    private static final String SQL_SELECT_ROUTE_ID_TRAIN_NUM_TRAVEL_TIME_JOIN =
            "SELECT a.route_id, tr.number, rt.travel_time " +
                    "FROM station_on_route a " +
                    "JOIN station_on_route b " +
                    "ON a.route_id = b.route_id " +
                    "AND a.station_id = ? " +
                    "AND b.station_id = ? " +
                    "JOIN trains tr " +
                    "ON a.route_id = tr.route_id " +
                    "JOIN routes rt " +
                    "ON rt.id = a.route_id";

    private static final String SQL_SELECT_INITIAL_AND_TERMINAL_STATIONS_JOIN =
            "SELECT sta.name, a.route_id, a.type_id, a.date, a.time, " +
                    "stb.name, b.route_id, b.type_id, b.date, b.time " +
                    "FROM station_on_route a " +
                    "JOIN station_on_route b " +
                    "ON a.route_id = ? " +
                    "AND a.route_id = b.route_id " +
                    "AND a.type_id = 1 " +
                    "AND b.type_id = 2 " +
                    "JOIN stations sta " +
                    "ON a.station_id = sta.id " +
                    "JOIN stations stb " +
                    "ON b.station_id = stb.id";

    private static final String SQL_SELECT_TRAIN_PLACES_BY_TRAIN_NUM =
            "SELECT * FROM train_places WHERE train_num = ?";

    private static final String SQL_SELECT_ALL_FROM_ROUTES_AND_TRAIN_JOIN =
            "SELECT rt.id, tr.number, rt.travel_time " +
                    "FROM routes rt JOIN trains tr ON rt.id = tr.route_id";

    private static RouteInfoDAOPostgresImpl instance;

    private RouteInfoDAOPostgresImpl() {
    }

    public static RouteInfoDAOPostgresImpl getInstance() {
        if (instance == null) {
            synchronized (RouteInfoDAOPostgresImpl.class) {
                if (instance == null) {
                    instance = new RouteInfoDAOPostgresImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public List<RouteInfo> getBasicRouteInfo(
            Connection connection, List<RouteInfo> basicRoutesInfo,
            int fromStationId, int toStationId)
            throws SQLException {

        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_SELECT_ROUTE_ID_TRAIN_NUM_TRAVEL_TIME_JOIN)) {

            DAOUtils.fillPreparedStatement(pstmt, fromStationId, toStationId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    basicRoutesInfo.add(getRouteInfo(resultSet));
                }
            }
        }
        return basicRoutesInfo;
    }

    @Override
    public List<RouteInfo> completeRouteInfo(
            Connection connection, List<RouteInfo> basicRoutesInfo)
            throws SQLException {

        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_SELECT_INITIAL_AND_TERMINAL_STATIONS_JOIN)) {

            for (RouteInfo routeInfo : basicRoutesInfo) {
                pstmt.setInt(1, routeInfo.getRoute().getId());

                try (ResultSet resultSet = pstmt.executeQuery()) {
                    if (resultSet.next()) {
                        routeInfo.setInitialStation(getInitialStation(resultSet));
                        routeInfo.setTerminalStation(getTerminalStation(resultSet));
                    }
                }
            }
        }
        return basicRoutesInfo;
    }

    @Override
    public List<RouteInfo> finishRouteInfo(
            Connection connection, List<RouteInfo> routesInfo)
            throws SQLException {

        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_SELECT_TRAIN_PLACES_BY_TRAIN_NUM)) {

            for (int i = 0; i < routesInfo.size(); i++) {
                RouteInfo routeInfo = routesInfo.get(i);
                pstmt.setInt(1, routeInfo.getTrain().getNumber());

                List<TrainPlace> trainPlaces = new ArrayList<>();

                try (ResultSet resultSet = pstmt.executeQuery()) {
                    while (resultSet.next()) {
                        trainPlaces.add(getTrainPlace(resultSet));
                    }
                }
                routeInfo.setTrainPlaces(trainPlaces);
            }
        }
        return routesInfo;
    }

    @Override
    public List<RouteInfo> getAllTrainsOnRoute(Connection connection)
            throws SQLException {

        List<RouteInfo> routesInfo = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     SQL_SELECT_ALL_FROM_ROUTES_AND_TRAIN_JOIN)) {

            while (resultSet.next()) {
                routesInfo.add(getRouteInfo(resultSet));
            }
        }
        return routesInfo;
    }
}
