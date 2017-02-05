package ua.nure.vorozhka.SummaryTask4.web.validator;

import ua.nure.vorozhka.SummaryTask4.exception.validate.IncorrectLoginException;
import ua.nure.vorozhka.SummaryTask4.exception.validate.ValidateException;

import java.util.regex.Pattern;

/**
 * Created by Stanislav on 20.01.2017.
 */
public class LoginValidator implements IValidator<String> {

    private static final String REGEX_FOR_LOGIN =
            "\\pL{5,16}";

    private static final Pattern PATTERN_FOR_LOGIN =
            Pattern.compile(REGEX_FOR_LOGIN);

    private static IValidator instance = new LoginValidator();

    private LoginValidator() {
    }

    public static IValidator getInstance(){
        return instance;
    }

    @Override
    public void validate(String login)
            throws ValidateException {

        if (!ValidatorUtils.suitPatter(PATTERN_FOR_LOGIN, login)) {
            throw new IncorrectLoginException("Incorrect user login, " + System.lineSeparator() +
                    "user login consist only latter, and " + System.lineSeparator() +
                    "max length of login - 16, min length - 5");
        }
    }
}
