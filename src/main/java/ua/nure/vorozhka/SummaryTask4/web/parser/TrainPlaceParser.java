package ua.nure.vorozhka.SummaryTask4.web.parser;

import ua.nure.vorozhka.SummaryTask4.db.model.constant.PlaceType;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.TrainPlace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class TrainPlaceParser implements IParser<TrainPlace> {

    private static final String REGEX_FOR_PATTERN =
            "trainNum - (\\d+), type - (\\w+), freePlaces - (\\d+), cost - (\\d+)";

    public static final Pattern PATTERN_FOR_PARSE_TRAIN_PLACE =
            Pattern.compile(REGEX_FOR_PATTERN);

    private static IParser instance = new TrainPlaceParser();

    private TrainPlaceParser() {
    }

    public static IParser getInstance() {
        return instance;
    }

    @Override
    public TrainPlace parse(String params) {
        TrainPlace trainPlace = new TrainPlace();
        Matcher matcher = PATTERN_FOR_PARSE_TRAIN_PLACE.matcher(params);

        if(matcher.find()){
            trainPlace.setTrainNum(Integer.parseInt(matcher.group(1)));
            trainPlace.setType(PlaceType.valueOf(matcher.group(2)));
            trainPlace.setFreePlaces(Integer.parseInt(matcher.group(3)));
            trainPlace.setCost(Integer.parseInt(matcher.group(4)));
        }

        return trainPlace;
    }
}
