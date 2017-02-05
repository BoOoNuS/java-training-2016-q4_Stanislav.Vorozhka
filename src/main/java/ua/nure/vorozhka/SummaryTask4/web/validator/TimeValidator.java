package ua.nure.vorozhka.SummaryTask4.web.validator;

import ua.nure.vorozhka.SummaryTask4.exception.validate.IncorrectTime;
import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;

import java.util.regex.Pattern;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class TimeValidator implements IValidator<String> {

    private static final String REGEX_FOR_TIME =
            "\\d{2}:\\d{2}:\\d{2}";

    private static final Pattern PATTERN_FOR_TIME =
            Pattern.compile(REGEX_FOR_TIME);

    private static IValidator instance = new TimeValidator();

    private TimeValidator() {
    }

    public static IValidator getInstance() {
        return instance;
    }

    @Override
    public void validate(String time)
            throws ValidateException {

        if (!ValidatorUtils.suitPatter(PATTERN_FOR_TIME, time)) {
            throw new IncorrectTime("Incorrect time");
        }
    }
}
