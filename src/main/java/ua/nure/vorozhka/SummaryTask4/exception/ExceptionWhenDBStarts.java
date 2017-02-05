package ua.nure.vorozhka.SummaryTask4.exception;

/**
 * Created by Stanislav on 22.01.2017.
 */
public class ExceptionWhenDBStarts extends RuntimeException {

    public ExceptionWhenDBStarts(String message, Throwable cause){
        super(message, cause);
    }
}
