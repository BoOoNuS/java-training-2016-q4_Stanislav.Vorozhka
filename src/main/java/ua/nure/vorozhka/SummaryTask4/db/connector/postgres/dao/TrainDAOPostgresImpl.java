package ua.nure.vorozhka.SummaryTask4.db.connector.postgres.dao;

import ua.nure.vorozhka.SummaryTask4.db.connector.DAOUtils;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.TrainDAO;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.Train;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class TrainDAOPostgresImpl extends TrainDAO {

    private static final String SQL_UPDATE_TRAIN_BY_TRAIN_NUMBER =
            "UPDATE trains SET route_id = ? WHERE number = ?";

    private static final String SQL_SELECT_ALL_TRAINS =
            "SELECT * FROM trains";

    private static final String SQL_SET_NULL_TO_ROUTE_ID_COLUMN_BY_TRAIN_NUM =
            "UPDATE trains SET route_id = ? WHERE number = ?";

    private static final String SQL_DELETE_TRAIN_BY_TRAIN_NUM =
            "DELETE FROM trains WHERE number = ?";

    private static TrainDAOPostgresImpl instance;

    private TrainDAOPostgresImpl() {
    }

    public static TrainDAOPostgresImpl getInstance() {
        if (instance == null) {
            synchronized (TrainDAOPostgresImpl.class) {
                if (instance == null) {
                    instance = new TrainDAOPostgresImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean updateTrain(Connection connection, Train train)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             SQL_UPDATE_TRAIN_BY_TRAIN_NUMBER)) {

            DAOUtils.fillPreparedStatement(
                    pstmt,
                    train.getRouteId(),
                    train.getNumber());

            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<Train> getTrains(Connection connection)
            throws SQLException {

        List<Train> trains = new ArrayList<>();

        try (Statement statement =
                     connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(
                    SQL_SELECT_ALL_TRAINS)) {

                while (resultSet.next()) {
                    trains.add(getTrain(resultSet));
                }
            }
        }
        return trains;
    }

    @Override
    public boolean setNullToRouteIdColumnByTrainNum(Connection connection, int number)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             SQL_SET_NULL_TO_ROUTE_ID_COLUMN_BY_TRAIN_NUM)) {

            fillPreparedStatementWithNullParam(number, pstmt);

            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean removeStationByTrainNum(Connection connection, int number)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(SQL_DELETE_TRAIN_BY_TRAIN_NUM)) {

            pstmt.setInt(1, number);

            return pstmt.executeUpdate() > 0;
        }
    }
}
