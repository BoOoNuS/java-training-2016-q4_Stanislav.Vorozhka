package ua.nure.vorozhka.SummaryTask4.db.model.entity;

import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;
import ua.nure.vorozhka.SummaryTask4.db.model.constant.StationType;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Stanislav on 20.01.2017.
 */
public class StationOnRoute implements Serializable {

    private Station station;
    private int routeId;
    private StationType type;
    private Date date;
    private String time;

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "station - " + station.getName() +
                ", routeId - " + routeId +
                ", type - " + type +
                ", date - " + date +
                ", time - " + time;
    }
}
