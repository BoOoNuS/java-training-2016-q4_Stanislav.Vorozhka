package ua.nure.vorozhka.SummaryTask4.web.validator;

import ua.nure.vorozhka.SummaryTask4.exception.validate.IncorrectLoginException;
import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;

import java.util.regex.Pattern;

/**
 * Created by Stanislav on 20.01.2017.
 */
public class StationNameValidator implements IValidator<String> {

    private static final String REGEX_FOR_STATION =
            "(\\pL|\\s){5,16}";

    private static final Pattern PATTERN_FOR_STATION =
            Pattern.compile(REGEX_FOR_STATION);

    private static IValidator instance = new StationNameValidator();

    private StationNameValidator() {
    }

    public static IValidator getInstance(){
        return instance;
    }

    @Override
    public void validate(String stationName)
            throws ValidateException {

        if (!ValidatorUtils.suitPatter(PATTERN_FOR_STATION, stationName)) {
            throw new IncorrectLoginException("Incorrect station name, " + System.lineSeparator() +
                    "station name consist only latter and spaces, and " + System.lineSeparator() +
                    "max length of station name - 16, min length - 5");
        }
    }
}
