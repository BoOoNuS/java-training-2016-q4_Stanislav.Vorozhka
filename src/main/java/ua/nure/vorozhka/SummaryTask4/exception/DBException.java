package ua.nure.vorozhka.SummaryTask4.exception;

/**
 * Created by Stanislav on 03.01.2017.
 */
public class DBException extends AppException {

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
