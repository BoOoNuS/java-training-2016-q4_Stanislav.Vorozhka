package ua.nure.vorozhka.SummaryTask4.db.connector.abstraction;

import ua.nure.vorozhka.SummaryTask4.db.model.bean.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Stanislav on 21.01.2017.
 */
public abstract class RouteDAO {

    public abstract boolean setRoute(Connection connection, Route route)
            throws SQLException;

    public abstract boolean updateRoute(Connection connection, Route route)
            throws SQLException;

    public abstract boolean removeRouteById(Connection connection, int routeId)
            throws SQLException;

    protected void fillPreparedStatement(PreparedStatement pstmt, Route route)
            throws SQLException {

        int counter = 1;
        pstmt.setLong(counter++, route.getTravelTime().getTime());
        pstmt.setInt(counter++, route.getId());
    }

    protected int getGeneratedKey(PreparedStatement pstmt)
            throws SQLException {

        int result = 0;

        try (ResultSet resultSet = pstmt.getGeneratedKeys()) {

            if (resultSet.next()){
                result = resultSet.getInt(1);
            }
        }
        return result;
    }
}
