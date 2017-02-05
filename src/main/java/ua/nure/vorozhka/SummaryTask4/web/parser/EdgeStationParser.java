package ua.nure.vorozhka.SummaryTask4.web.parser;

import ua.nure.vorozhka.SummaryTask4.db.model.constant.StationType;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.EdgeStation;

import java.sql.Date;
import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class EdgeStationParser implements IParser<EdgeStation> {

    private static final String REGEX_FOR_PATTERN =
            "stationName - (\\w+), " +
                    "routeId - (\\d+), " +
                    "type - (\\w+), " +
                    "date - (\\d+-\\d+-\\d+), " +
                    "time - (\\d+:\\d+:\\d+)";

    private static final Pattern PATTERN_FOR_PARSE_EDGE_STATION = Pattern.compile(REGEX_FOR_PATTERN);

    private static IParser instance = new EdgeStationParser();

    private EdgeStationParser() {
    }

    public static IParser getInstance() {
        return instance;
    }

    @Override
    public EdgeStation parse(String params) {
        EdgeStation edgeStation = new EdgeStation();
        Matcher matcher = PATTERN_FOR_PARSE_EDGE_STATION.matcher(params);

        if(matcher.find()){
            edgeStation.setStationName(matcher.group(1));
            edgeStation.setRouteId(Integer.parseInt(matcher.group(2)));
            edgeStation.setType(StationType.valueOf(matcher.group(3).toUpperCase()));
            edgeStation.setDate(Date.valueOf(matcher.group(4)));
            edgeStation.setTime(Time.valueOf(matcher.group(5)));
        }
        return edgeStation;
    }
}
