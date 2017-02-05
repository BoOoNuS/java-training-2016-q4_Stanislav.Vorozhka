package ua.nure.vorozhka.SummaryTask4.db.model.entity;

import ua.nure.vorozhka.SummaryTask4.db.model.bean.Route;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 12.01.2017.
 */
public class RouteInfo implements Serializable {

    private Route route;
    private Train train;
    private EdgeStation initialStation;
    private EdgeStation terminalStation;
    private List<TrainPlace> trainPlaces = new ArrayList<>();

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public EdgeStation getInitialStation() {
        return initialStation;
    }

    public void setInitialStation(EdgeStation initialStation) {
        this.initialStation = initialStation;
    }

    public EdgeStation getTerminalStation() {
        return terminalStation;
    }

    public void setTerminalStation(EdgeStation terminalStation) {
        this.terminalStation = terminalStation;
    }

    public List<TrainPlace> getTrainPlaces() {
        return trainPlaces;
    }

    public void setTrainPlaces(List<TrainPlace> trainPlaces) {
        this.trainPlaces = trainPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteInfo routeInfo = (RouteInfo) o;

        if (route != null ? !route.equals(routeInfo.route) : routeInfo.route != null) return false;
        if (train != null ? !train.equals(routeInfo.train) : routeInfo.train != null) return false;
        if (initialStation != null ? !initialStation.equals(routeInfo.initialStation) : routeInfo.initialStation != null)
            return false;
        if (terminalStation != null ? !terminalStation.equals(routeInfo.terminalStation) : routeInfo.terminalStation != null)
            return false;
        return trainPlaces != null ? trainPlaces.equals(routeInfo.trainPlaces) : routeInfo.trainPlaces == null;
    }

    @Override
    public int hashCode() {
        int result = route != null ? route.hashCode() : 0;
        result = 31 * result + (train != null ? train.hashCode() : 0);
        result = 31 * result + (initialStation != null ? initialStation.hashCode() : 0);
        result = 31 * result + (terminalStation != null ? terminalStation.hashCode() : 0);
        result = 31 * result + (trainPlaces != null ? trainPlaces.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "route - " + route +
                ", trainNum - " + train.getNumber() +
                ", initialStation- " + initialStation +
                ", terminalStation - " + terminalStation +
                ", trainPlaces - " + trainPlaces;
    }
}
