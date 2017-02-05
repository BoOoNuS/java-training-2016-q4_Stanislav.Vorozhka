package ua.nure.vorozhka.SummaryTask4.db.connector.abstraction;

import ua.nure.vorozhka.SummaryTask4.db.model.entity.Train;

import java.sql.*;
import java.util.List;

/**
 * Created by Stanislav on 21.01.2017.
 */
public abstract class TrainDAO {

    public abstract boolean updateTrain(Connection connection, Train train)
            throws SQLException;

    public abstract List<Train> getTrains(Connection connection)
            throws SQLException;

    protected Train getTrain(ResultSet resultSet)
            throws SQLException {

        Train train = new Train();
        train.setNumber(resultSet.getInt("number"));
        train.setRouteId(resultSet.getInt("route_id"));
        return train;
    }

    public abstract boolean setNullToRouteIdColumnByTrainNum(
            Connection connection, int number)
            throws SQLException;

    protected void fillPreparedStatementWithNullParam(
            int number, PreparedStatement pstmt)
            throws SQLException {

        int counter = 1;
        pstmt.setNull(counter++, Types.NULL);
        pstmt.setInt(counter++, number);
    }

    public abstract boolean removeStationByTrainNum(
            Connection connection, int number)
            throws SQLException;
}
