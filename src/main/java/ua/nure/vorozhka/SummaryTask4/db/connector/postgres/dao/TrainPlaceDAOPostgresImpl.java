package ua.nure.vorozhka.SummaryTask4.db.connector.postgres.dao;

import ua.nure.vorozhka.SummaryTask4.db.connector.DAOUtils;
import ua.nure.vorozhka.SummaryTask4.db.connector.abstraction.TrainPlaceDAO;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.TrainPlace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Stanislav on 20.01.2017.
 */
public class TrainPlaceDAOPostgresImpl extends TrainPlaceDAO {

    private static final String SQL_UPDATE_FREE_PLACES_BY_TRAIN_NUM_AND_TYPE_ID =
            "UPDATE train_places SET free_places = ? WHERE train_num = ? AND type_id = ?";

    private static final String SQL_SELECT_TRAIN_PLACE_BY_TRAIN_NUM_AND_TYPE_ID =
            "SELECT * FROM train_places WHERE train_num = ? AND type_id = ?";

    private static TrainPlaceDAOPostgresImpl instance;

    private TrainPlaceDAOPostgresImpl() {
    }

    public static TrainPlaceDAOPostgresImpl getInstance() {
        if (instance == null) {
            synchronized (TrainPlaceDAOPostgresImpl.class) {
                if (instance == null) {
                    instance = new TrainPlaceDAOPostgresImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean updateTrainPlace(
            Connection connection, TrainPlace trainPlace)
            throws SQLException {

        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             SQL_UPDATE_FREE_PLACES_BY_TRAIN_NUM_AND_TYPE_ID)) {

            int decrementedFreePlaces = trainPlace.getFreePlaces() - 1;
            int placeTypeId = trainPlace.getType().ordinal() + 1;

            DAOUtils.fillPreparedStatement(
                    pstmt, decrementedFreePlaces,
                    trainPlace.getTrainNum(), placeTypeId);

            return pstmt.executeUpdate() > 0;
        }
    }

    @Override
    public TrainPlace getTrainPlaceByTrainNumAndPlaceTypeId(
            Connection connection, int trainNum, int placeTypeId)
            throws SQLException {

        TrainPlace trainPlace = new TrainPlace();

        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             SQL_SELECT_TRAIN_PLACE_BY_TRAIN_NUM_AND_TYPE_ID)) {

            DAOUtils.fillPreparedStatement(pstmt, trainNum, placeTypeId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    trainPlace = getTrainPlace(resultSet, trainNum, placeTypeId);
                }
            }
        }
        return trainPlace;
    }
}
