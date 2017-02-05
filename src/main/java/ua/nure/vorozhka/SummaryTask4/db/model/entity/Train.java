package ua.nure.vorozhka.SummaryTask4.db.model.entity;

import java.io.Serializable;

/**
 * Created by Stanislav on 05.01.2017.
 */
public class Train implements Serializable {

    private int number;
    private int routeId;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if (number != train.number) return false;
        return routeId == train.routeId;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + routeId;
        return result;
    }

    @Override
    public String toString() {
        return "number - " + number +
                ", routeId - " + routeId;
    }
}
