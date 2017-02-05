package ua.nure.vorozhka.SummaryTask4.web.validator;

import ua.nure.vorozhka.SummaryTask4.exception.validate.IncorrectDate;
import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;

import java.util.regex.Pattern;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class DateValidator implements IValidator<String> {

    private static final String REGEX_FOR_DATE =
            "\\d{4}-\\d{2}-\\d{2}";

    private static final Pattern PATTERN_FOR_DATE =
            Pattern.compile(REGEX_FOR_DATE);

    private static IValidator instance = new DateValidator();

    private DateValidator() {
    }

    public static IValidator getInstance() {
        return instance;
    }

    @Override
    public void validate(String date)
            throws ValidateException {

        if (!ValidatorUtils.suitPatter(PATTERN_FOR_DATE, date)) {
            throw new IncorrectDate("Incorrect date");
        }
    }
}
