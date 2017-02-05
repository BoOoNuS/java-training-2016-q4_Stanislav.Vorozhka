package ua.nure.vorozhka.SummaryTask4.exception;

/**
 * Created by Stanislav on 03.01.2017.
 */
public interface ExceptionMessages {

    String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";
    String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the date source";
    String ERR_FAILED_TO_CLOSE_CONNECTION = "Failed to close the connection";
    String ERR_USER_DOES_NOT_EXIST = "User with such password and username does not exist";
    String ERR_USER_HAS_NOT_INSERTED = "User has not inserted";
    String ERR_CANNOT_ROLL_BACK = "Cannot roll back";
    String ERR_CANNOT_GET_STATIONS = "Cannot get stations";
    String ERR_STATION_HAS_NOT_INSERTED = "Station has not inserted";
    String ERR_STATION_HAS_NOT_REMOVED = "Station has not removed";
    String ERR_STATION_HAS_NOT_UPDATED = "Station has not updated";
    String ERR_SELECTING_DATABASE_ERROR_OCCURRED = "When selecting a database error occurred";
    String ERR_FAILED_TO_DECREMENT_FREE_SEATS = "Failed to decrement the number of available places";
    String ERR_STATION_ON_ROUTE_HAS_NOT_UPDATED = "Station on route has not updated";
    String ERR_STATION_ON_ROUTE_HAS_NOT_INSERTED = "Station on route has not inserted";
    String ERR_STATION_ON_ROUTE_HAS_NOT_REMOVED = "Station on route has not removed";
    String ERR_CANNOT_GET_STATION_ON_ROUTE = "Cannot get station on route";
    String ERR_TRAIN_HAS_NOT_INSERTED = "Train on route has not inserted";
    String ERR_CANNOT_GET_TRAINS = "Cannot get trains";
    String ERR_ROUTE_HAS_NOT_INSERTED = "Route has not inserted";
    String ERR_ROUTE_HAS_NOT_REMOVED = "Route has not removed";
    String ERR_ROUTE_HAS_NOT_UPDATED = "Route has not updated";
    String ERR_CANNOT_GET_TRAIN_PLACE = "Cannot get train place";
}
