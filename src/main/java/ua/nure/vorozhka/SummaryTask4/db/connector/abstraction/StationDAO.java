package ua.nure.vorozhka.SummaryTask4.db.connector.abstraction;

import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Stanislav on 18.01.2017.
 */
public abstract class StationDAO {

    public abstract boolean updateStation(
            Connection connection, Station station)
            throws SQLException;

    public abstract boolean removeStationById(
            Connection connection, int stationId)
            throws SQLException;

    public abstract boolean setStation(
            Connection connection, Station station)
            throws SQLException;

    public abstract List<Station> getStations(Connection connection)
            throws SQLException;

    public abstract Station getStationByName(
            Connection connection, String stationName)
            throws SQLException;

    public abstract Station getStationByRouteIdAndStationTypeId(
            Connection connection, int routeId, int stationId)
            throws SQLException;

    protected Station getStation(ResultSet resultSet)
            throws SQLException {

        Station station = new Station();
        station.setId(resultSet.getInt("id"));
        station.setName(resultSet.getString("name"));

        return station;
    }
}
