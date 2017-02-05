package ua.nure.vorozhka.SummaryTask4.db.model.entity;

import ua.nure.vorozhka.SummaryTask4.db.model.constant.StationType;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Stanislav on 05.01.2017.
 */
public class EdgeStation implements Serializable {

    private String stationName;
    private int routeId;
    private StationType type;
    private Date date;
    private Time time;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public StationType getType() {
        return type;
    }

    public void setType(StationType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EdgeStation that = (EdgeStation) o;

        if (routeId != that.routeId) return false;
        if (stationName != null ? !stationName.equals(that.stationName) : that.stationName != null) return false;
        if (type != that.type) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return time != null ? time.equals(that.time) : that.time == null;
    }

    @Override
    public int hashCode() {
        int result = stationName != null ? stationName.hashCode() : 0;
        result = 31 * result + routeId;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "stationName - " + stationName +
                ", routeId - " + routeId +
                ", type - " + type.getName() +
                ", date - " + date +
                ", time - " + time;
    }
}
