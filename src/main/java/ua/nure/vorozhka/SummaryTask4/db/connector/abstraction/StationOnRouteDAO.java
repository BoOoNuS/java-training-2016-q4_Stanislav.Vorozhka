package ua.nure.vorozhka.SummaryTask4.db.connector.abstraction;

import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;
import ua.nure.vorozhka.SummaryTask4.db.model.constant.StationType;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.StationOnRoute;
import ua.nure.vorozhka.SummaryTask4.web.parser.IParser;
import ua.nure.vorozhka.SummaryTask4.web.parser.TimeParserForWaySOR;

import java.sql.*;
import java.util.List;

/**
 * Created by Stanislav on 20.01.2017.
 */
public abstract class StationOnRouteDAO {

    private static final IParser<String> TIME_PARSER = TimeParserForWaySOR.getInstance();

    public abstract boolean setStationOnRoute(
            Connection connection, StationOnRoute stationOnRoute)
            throws SQLException;

    public abstract boolean updateStationOnRoute(
            Connection connection, StationOnRoute stationOnRoute)
            throws SQLException;

    public abstract List<StationOnRoute> getWayStationsOnRouteByRoute(
            Connection connection, int routeId)
            throws SQLException;

    public abstract boolean removeStationOnRouteByRouteIdAndStationId(
            Connection connection, int routeId, int stationId)
            throws SQLException;

    protected void fillPreparedStatement(
            PreparedStatement pstmt, StationOnRoute stationOnRoute)
            throws SQLException {

        int typeId = stationOnRoute.getType().ordinal() + 1;

        int counter = 1;
        pstmt.setInt(counter++, stationOnRoute.getStation().getId());
        pstmt.setLong(counter++, stationOnRoute.getDate().getTime());
        pstmt.setString(counter++, stationOnRoute.getTime());
        pstmt.setInt(counter++, stationOnRoute.getRouteId());
        pstmt.setInt(counter++, typeId);
    }

    protected StationOnRoute getStationOnRoute(ResultSet resultSet, int routeId)
            throws SQLException {

        Station station = new Station();
        station.setId(resultSet.getInt(1));
        station.setName(resultSet.getString(2));

        StationOnRoute stationOnRoute = new StationOnRoute();
        int stationTypeId = resultSet.getInt(3) - 1;

        String longTimes = resultSet.getString(5);
        String strTime = TIME_PARSER.parse(longTimes);

        stationOnRoute.setStation(station);
        stationOnRoute.setRouteId(routeId);
        stationOnRoute.setType(StationType.getStationType(stationTypeId));
        stationOnRoute.setDate(new Date(resultSet.getLong(4)));
        stationOnRoute.setTime(strTime);

        return stationOnRoute;
    }
}
