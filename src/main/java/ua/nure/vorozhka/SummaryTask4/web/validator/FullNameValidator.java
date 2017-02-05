package ua.nure.vorozhka.SummaryTask4.web.validator;

import ua.nure.vorozhka.SummaryTask4.exception.validate.IncorrectFullNameException;
import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;

import java.util.regex.Pattern;

/**
 * Created by Stanislav on 20.01.2017.
 */
public class FullNameValidator implements IValidator<String> {

    private static final String REGEX_FOR_FULL_NAME =
            "(\\pL|\\s){4,36}";

    private static final Pattern PATTERN_FOR_FULL_NAME =
            Pattern.compile(REGEX_FOR_FULL_NAME);

    private static IValidator instance = new FullNameValidator();

    private FullNameValidator() {
    }

    public static IValidator getInstance(){
        return instance;
    }

    @Override
    public void validate(String fullName)
            throws ValidateException {

        if (!ValidatorUtils.suitPatter(PATTERN_FOR_FULL_NAME, fullName)) {
            throw new IncorrectFullNameException("Incorrect full name, " + System.lineSeparator() +
                    "full name consist only latter and spaces, and " + System.lineSeparator() +
                    "max length of full name - 36, min length - 4");
        }
    }
}
