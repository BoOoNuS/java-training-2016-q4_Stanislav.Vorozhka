package ua.nure.vorozhka.SummaryTask4.web.parser;

import ua.nure.vorozhka.SummaryTask4.db.model.entity.EdgeStation;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.RouteInfo;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.Train;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class RouteInfoParser implements IParser<RouteInfo> {

    private static final IParser<EdgeStation> EDGE_STATION_PARSER = EdgeStationParser.getInstance();
    private static final IParser<Train> TRAIN_PARSER = TrainParser.getInstance();

    private static final String REGEX_FOR_PATTERN =
            "(stationName - \\w+, " +
                    "routeId - \\d+, " +
                    "type - \\w+, " +
                    "date - \\d+-\\d+-\\d+, " +
                    "time - \\d+:\\d+:\\d+)";

    private static final Pattern PATTERN_FOR_PARSE_EDGE_STATION = Pattern.compile(REGEX_FOR_PATTERN);

    private static IParser instance = new RouteInfoParser();

    private RouteInfoParser() {
    }

    public static IParser getInstance() {
        return instance;
    }

    @Override
    public RouteInfo parse(String params) {
        RouteInfo routeInfo = new RouteInfo();

        routeInfo.setTrain(TRAIN_PARSER.parse(params));

        Matcher matcher = PATTERN_FOR_PARSE_EDGE_STATION.matcher(params);
        if (matcher.find()) {
            routeInfo.setInitialStation(EDGE_STATION_PARSER.parse(matcher.group(1)));
        }
        if (matcher.find()) {
            routeInfo.setTerminalStation(EDGE_STATION_PARSER.parse(matcher.group(1)));
        }

        return routeInfo;
    }
}
