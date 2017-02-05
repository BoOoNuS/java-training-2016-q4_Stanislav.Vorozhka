package ua.nure.vorozhka.SummaryTask4.db.connector.postgres.dao;

import ua.nure.vorozhka.SummaryTask4.db.connector.DAOUtils;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.StationOnRouteDAO;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.StationOnRoute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 20.01.2017.
 */
public class StationOnRouteDAOPostgresImpl extends StationOnRouteDAO {

    private static final String SQL_INSERT_STATION_ON_ROUTE =
            "INSERT INTO station_on_route " +
                    "(station_id, date, time, route_id, type_id) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_STATION_ON_ROUTE =
            "UPDATE station_on_route SET station_id = ?, " +
                    "date = ?, time = ? WHERE route_id = ? AND type_id = ?";

    private static final String SQL_SELECT_WAY_STATIONS_ON_ROUTE_BY_ROUTE_ID_STATION_JOIN =
            "SELECT st.id, st.name, sor.type_id, sor.date, sor.time " +
                    "FROM station_on_route sor JOIN stations st " +
                    "ON sor.route_id = ? AND sor.type_id = 3 AND sor.station_id = st.id";

    private static final String SQL_DELETE_STATION_ON_ROUTE_BY_ROUTE_ID_AND_STATION_ID =
            "DELETE FROM station_on_route WHERE route_id = ? AND station_id = ?";

    private static StationOnRouteDAOPostgresImpl instance;

    private StationOnRouteDAOPostgresImpl() {
    }

    public static StationOnRouteDAOPostgresImpl getInstance() {
        if (instance == null) {
            synchronized (StationOnRouteDAOPostgresImpl.class) {
                if (instance == null) {
                    instance = new StationOnRouteDAOPostgresImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean setStationOnRoute(
            Connection connection, StationOnRoute stationOnRoute)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_INSERT_STATION_ON_ROUTE)) {

            fillPreparedStatement(pstmt, stationOnRoute);

            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateStationOnRoute(
            Connection connection, StationOnRoute stationOnRoute)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_UPDATE_STATION_ON_ROUTE)) {

            fillPreparedStatement(pstmt, stationOnRoute);

            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<StationOnRoute> getWayStationsOnRouteByRoute(
            Connection connection, int routeId)
            throws SQLException {


        List<StationOnRoute> stationsOnRoute = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_SELECT_WAY_STATIONS_ON_ROUTE_BY_ROUTE_ID_STATION_JOIN)) {

            pstmt.setInt(1, routeId);

            try (ResultSet resultSet = pstmt.executeQuery()) {

                while (resultSet.next()) {
                    stationsOnRoute.add(getStationOnRoute(resultSet, routeId));
                }
            }
        }
        return stationsOnRoute;
    }

    @Override
    public boolean removeStationOnRouteByRouteIdAndStationId(
            Connection connection, int routeId, int stationId)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             SQL_DELETE_STATION_ON_ROUTE_BY_ROUTE_ID_AND_STATION_ID)) {

            DAOUtils.fillPreparedStatement(pstmt, routeId, stationId);

            return pstmt.executeUpdate() > 0;
        }
    }
}
