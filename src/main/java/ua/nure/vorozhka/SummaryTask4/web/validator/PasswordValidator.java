package ua.nure.vorozhka.SummaryTask4.web.validator;

import ua.nure.vorozhka.SummaryTask4.exception.validate.IncorrectPasswordException;
import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;

import java.util.regex.Pattern;

/**
 * Created by Stanislav on 20.01.2017.
 */
public class PasswordValidator implements IValidator<String> {

    private static final String REGEX_FOR_PASSWORD =
            "\\pL{5,16}";

    private static final Pattern PATTERN_FOR_PASSWORD =
            Pattern.compile(REGEX_FOR_PASSWORD);

    private static IValidator instance = new PasswordValidator();

    private PasswordValidator() {
    }

    public static IValidator getInstance(){
        return instance;
    }

    @Override
    public void validate(String password)
            throws ValidateException {

        if (!ValidatorUtils.suitPatter(PATTERN_FOR_PASSWORD, password)) {
            throw new IncorrectPasswordException("Incorrect user password, " + System.lineSeparator() +
                    "user password consist only latter, and " + System.lineSeparator() +
                    "max length of password - 16, min length - 5");
        }
    }
}
