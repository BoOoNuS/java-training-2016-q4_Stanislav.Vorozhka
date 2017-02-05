package ua.nure.vorozhka.SummaryTask4.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Stanislav on 20.01.2017.
 */
public final class ValidatorUtils {

    private ValidatorUtils() {

    }

    public static boolean suitPatter(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}
