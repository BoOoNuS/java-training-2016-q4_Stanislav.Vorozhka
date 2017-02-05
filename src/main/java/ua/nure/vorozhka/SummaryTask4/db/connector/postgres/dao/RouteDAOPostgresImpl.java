package ua.nure.vorozhka.SummaryTask4.db.connector.postgres.dao;

import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.RouteDAO;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class RouteDAOPostgresImpl extends RouteDAO {

    private static final String SQL_INSERT_INTO_ROUTES =
            "INSERT INTO routes (travel_time) VALUES (?)";

    private static final String SQL_UPDATE_ROUTE_BY_ID =
            "UPDATE routes SET travel_time = ? WHERE id = ?";

    private static final String SQL_DELETE_ROUTE_BY_ID =
            "DELETE FROM routes WHERE id = ?";

    private static RouteDAOPostgresImpl instance;

    private RouteDAOPostgresImpl() {
    }

    public static RouteDAOPostgresImpl getInstance() {
        if (instance == null) {
            synchronized (RouteDAOPostgresImpl.class) {
                if (instance == null) {
                    instance = new RouteDAOPostgresImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean setRoute(Connection connection, Route route)
            throws SQLException {

        try (PreparedStatement pstmt = connection.prepareStatement(
                SQL_INSERT_INTO_ROUTES,
                PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, route.getTravelTime().getTime());
            pstmt.executeUpdate();

            int result = getGeneratedKey(pstmt);
            if(result > 0) {
                route.setId(result);
            }
        }
        return route.getId() > 0;
    }

    @Override
    public boolean updateRoute(Connection connection, Route route)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_UPDATE_ROUTE_BY_ID)) {

            fillPreparedStatement(pstmt, route);

            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean removeRouteById(Connection connection, int routeId)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_DELETE_ROUTE_BY_ID)) {

            pstmt.setInt(1, routeId);

            return pstmt.executeUpdate() > 0;
        }
    }
}
