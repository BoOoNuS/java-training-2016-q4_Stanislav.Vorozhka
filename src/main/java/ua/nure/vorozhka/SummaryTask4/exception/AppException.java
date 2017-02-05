package ua.nure.vorozhka.SummaryTask4.exception;

/**
 * Created by Stanislav on 03.01.2017.
 */
public class AppException extends Exception {

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }

}
