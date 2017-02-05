package ua.nure.vorozhka.SummaryTask4.db.connector.postgres.dao;

import ua.nure.vorozhka.SummaryTask4.db.connector.DAOUtils;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.StationDAO;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 12.01.2017.
 */
public final class StationDAOPostgresImpl extends StationDAO {

    private static final String SQL_SELECT_ALL_STATIONS =
            "SELECT * FROM stations";

    private static final String SQL_INSERT_STATION =
            "INSERT INTO stations (name) VALUES (?)";

    private static final String SQL_DELETE_FROM_STATIONS_BY_ID =
            "DELETE FROM stations WHERE id = ?";

    private static final String SQL_UPDATE_STATION_BY_ID =
            "UPDATE stations SET name = ? WHERE id = ?";

    private static final String SQL_SELECT_STATIONS_BY_NAME = "SELECT * FROM stations WHERE name = ?";

    private static final String SQL_SELECT_STATION_BY_ROUTE_ID_AND_TYPE_ID =
            "SELECT s.id, s.name " +
                    "FROM station_on_route sor " +
                    "JOIN stations s " +
                    "ON sor.route_id = ? " +
                    "AND sor.type_id = ? " +
                    "AND sor.station_id = s.id";

    private static StationDAOPostgresImpl instance;

    private StationDAOPostgresImpl() {
    }

    public static StationDAOPostgresImpl getInstance() {
        if (instance == null) {
            synchronized (StationDAOPostgresImpl.class) {
                if (instance == null) {
                    instance = new StationDAOPostgresImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Station getStationByName(
            Connection connection, String stationName)
            throws SQLException {

        Station station = null;
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_SELECT_STATIONS_BY_NAME)) {

            pstmt.setString(1, stationName);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    station = getStation(resultSet);
                }
            }
        }
        return station;
    }

    @Override
    public List<Station> getStations(Connection connection)
            throws SQLException {

        List<Station> stations = new ArrayList<>();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_STATIONS)) {

            while (resultSet.next()) {
                stations.add(getStation(resultSet));
            }
        }
        return stations;
    }

    @Override
    public Station getStationByRouteIdAndStationTypeId(
            Connection connection, int routeId, int stationId)
            throws SQLException {

        Station station = new Station();
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_SELECT_STATION_BY_ROUTE_ID_AND_TYPE_ID)) {

            DAOUtils.fillPreparedStatement(pstmt, routeId, stationId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    station.setId(resultSet.getInt(1));
                    station.setName(resultSet.getString(2));
                }
            }
        }
        return station;
    }

    @Override
    public boolean setStation(Connection connection, Station station)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_INSERT_STATION)) {

            pstmt.setString(1, station.getName());
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean removeStationById(Connection connection, int stationId)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_DELETE_FROM_STATIONS_BY_ID)) {

            pstmt.setInt(1, stationId);
            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateStation(Connection connection, Station station)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_UPDATE_STATION_BY_ID)) {

            DAOUtils.fillPreparedStatementPostfix(
                    pstmt, station.getId(), station.getName());

            return pstmt.executeUpdate() > 0;
        }
    }
}
