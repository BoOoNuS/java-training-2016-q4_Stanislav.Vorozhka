package ua.nure.vorozhka.SummaryTask4.web.parser;

import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Stanislav on 21.01.2017.
 */
public class TimeParserForWaySOR implements IParser<String> {

    private static final String REGEX_FOR_PATTERN = "(\\d+)";

    private static final Pattern PATTERN_FOR_PARSE_TIME = Pattern.compile(REGEX_FOR_PATTERN);

    private static IParser instance = new TimeParserForWaySOR();

    private TimeParserForWaySOR() {
    }

    public static IParser getInstance() {
        return instance;
    }

    @Override
    public String parse(String longTimes) {
        StringBuilder sbTime = new StringBuilder();

        Matcher matcher = PATTERN_FOR_PARSE_TIME.matcher(longTimes);

        while (matcher.find()) {
            sbTime.append(new Time(Long.parseLong(matcher.group(1))).toString()).append("/");
        }

        return sbTime.toString();
    }
}
