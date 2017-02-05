package ua.nure.vorozhka.SummaryTask4.web.parser;

/**
 * Created by Stanislav on 21.01.2017.
 */
public interface IParser<T> {

    T parse(String field);
}
