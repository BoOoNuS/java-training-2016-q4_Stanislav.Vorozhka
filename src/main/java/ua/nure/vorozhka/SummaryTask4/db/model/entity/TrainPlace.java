package ua.nure.vorozhka.SummaryTask4.db.model.entity;

import ua.nure.vorozhka.SummaryTask4.db.model.constant.PlaceType;

import java.io.Serializable;

/**
 * Created by Stanislav on 10.01.2017.
 */
public class TrainPlace implements Serializable {

    private int trainNum;
    private PlaceType type;
    private int freePlaces;
    private int cost;

    public int getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(int trainNum) {
        this.trainNum = trainNum;
    }

    public PlaceType getType() {
        return type;
    }

    public void setType(PlaceType type) {
        this.type = type;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "trainNum - " + trainNum +
                ", type - " + type.name() +
                ", freePlaces - " + freePlaces +
                ", cost - " + cost;
    }
}
