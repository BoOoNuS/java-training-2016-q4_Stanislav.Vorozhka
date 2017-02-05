package ua.nure.vorozhka.SummaryTask4.db.model.bean;

import java.io.Serializable;
import java.sql.Time;

/**
 * Created by Stanislav on 05.01.2017.
 */
public class Route implements Serializable {

    private int id;
    private Time travelTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Time travelTime) {
        this.travelTime = travelTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route = (Route) o;

        if (id != route.id) return false;
        return travelTime != null ? travelTime.equals(route.travelTime) : route.travelTime == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (travelTime != null ? travelTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "id - " + id +
                ", travelTime - " + travelTime;
    }
}
