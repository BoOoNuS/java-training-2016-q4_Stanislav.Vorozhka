package ua.nure.vorozhka.SummaryTask4.web.parser;

import ua.nure.vorozhka.SummaryTask4.db.model.entity.Train;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class TrainParser implements IParser<Train> {

    private static final String REGEX_FOR_PATTERN = "\\w+ - (\\d), \\w+ - (\\d)";
    private static final Pattern PATTERN_FRO_PARSE_TRAIN = Pattern.compile(REGEX_FOR_PATTERN);

    private static IParser instance = new TrainParser();

    private TrainParser() {
    }

    public static IParser getInstance() {
        return instance;
    }

    @Override
    public Train parse(String params) {
        Train train = new Train();
        Matcher matcher = PATTERN_FRO_PARSE_TRAIN.matcher(params);
        if(matcher.find()){
            train.setNumber(Integer.parseInt(matcher.group(1)));
            train.setRouteId(Integer.parseInt(matcher.group(2)));
        }
        return train;
    }
}
