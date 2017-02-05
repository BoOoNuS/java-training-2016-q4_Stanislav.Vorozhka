package ua.nure.vorozhka.SummaryTask4.db.connector.abstraction;

import ua.nure.vorozhka.SummaryTask4.db.model.constant.PlaceType;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.TrainPlace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Stanislav on 20.01.2017.
 */
public abstract class TrainPlaceDAO {

    public abstract boolean updateTrainPlace(
            Connection connection, TrainPlace trainPlace)
            throws SQLException;

    public abstract TrainPlace getTrainPlaceByTrainNumAndPlaceTypeId(
            Connection connection, int trainNum, int placeTypeId)
            throws SQLException;

    protected TrainPlace getTrainPlace(
            ResultSet resultSet, int trainNum, int placeTypeId)
            throws SQLException {

        TrainPlace trainPlace = new TrainPlace();
        trainPlace.setTrainNum(trainNum);
        trainPlace.setType(PlaceType.getPlaceType(placeTypeId));
        trainPlace.setFreePlaces(resultSet.getInt("free_places"));
        trainPlace.setCost(resultSet.getInt("cost"));

        return trainPlace;
    }
}
