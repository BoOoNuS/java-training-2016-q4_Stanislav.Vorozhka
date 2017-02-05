package ua.nure.vorozhka.SummaryTask4.db.connector.abstraction;

import ua.nure.vorozhka.SummaryTask4.db.model.bean.Route;
import ua.nure.vorozhka.SummaryTask4.db.model.bean.Station;
import ua.nure.vorozhka.SummaryTask4.db.model.constant.PlaceType;
import ua.nure.vorozhka.SummaryTask4.db.model.constant.StationType;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.EdgeStation;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.RouteInfo;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.Train;
import ua.nure.vorozhka.SummaryTask4.db.model.entity.TrainPlace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav on 18.01.2017.
 */
public abstract class RoutesInfoDAO {

    public List<RouteInfo> templateMethod(
            Connection connection, Station fromStations, Station toStations)
            throws SQLException {

        List<RouteInfo> routesInfo = new ArrayList<>();

        if (fromStations != null && toStations != null) {
            getBasicRouteInfo(
                    connection, routesInfo,
                    fromStations.getId(),
                    toStations.getId());
        }

        completeRouteInfo(connection, routesInfo);
        finishRouteInfo(connection, routesInfo);

        return routesInfo;
    }

    public abstract List<RouteInfo> finishRouteInfo(
            Connection connection, List<RouteInfo> routesInfo)
            throws SQLException;

    protected abstract List<RouteInfo> completeRouteInfo(
            Connection connection, List<RouteInfo> basicRoutesInfo)
            throws SQLException;

    protected abstract List<RouteInfo> getBasicRouteInfo(
            Connection connection, List<RouteInfo> basicRoutesInfo,
            int fromStationId, int toStationId)
            throws SQLException;

    public abstract List<RouteInfo> getAllTrainsOnRoute(Connection connection)
            throws SQLException;

    protected TrainPlace getTrainPlace(ResultSet resultSet)
            throws SQLException {

        TrainPlace trainPlace = new TrainPlace();
        trainPlace.setTrainNum(resultSet.getInt(1));
        trainPlace.setType(PlaceType.getPlaceType(resultSet.getInt(2)));
        trainPlace.setFreePlaces(resultSet.getInt(3));
        trainPlace.setCost(resultSet.getInt(4));
        return trainPlace;
    }

    protected EdgeStation getInitialStation(ResultSet resultSet)
            throws SQLException {

        return getStationOnRoute(resultSet, 1);
    }

    protected EdgeStation getTerminalStation(ResultSet resultSet)
            throws SQLException {

        return getStationOnRoute(resultSet, 6);
    }

    protected EdgeStation getStationOnRoute(
            ResultSet resultSet, int counter)
            throws SQLException {

        EdgeStation sor = new EdgeStation();
        sor.setStationName(resultSet.getString(counter++));
        sor.setRouteId(resultSet.getInt(counter++));
        sor.setType(StationType.getStationType(resultSet.getInt(counter++)));
        sor.setDate(new Date(resultSet.getLong(counter++)));
        sor.setTime(new Time(Long.parseLong(resultSet.getString(counter++))));
        return sor;
    }

    protected RouteInfo getRouteInfo(ResultSet resultSet)
            throws SQLException {

        RouteInfo routeInfo = new RouteInfo();
        Route route = new Route();
        route.setId(resultSet.getInt(1));
        route.setTravelTime(new Time(resultSet.getLong(3)));
        routeInfo.setRoute(route);
        Train train = new Train();
        train.setNumber(resultSet.getInt(2));
        train.setRouteId(route.getId());
        routeInfo.setTrain(train);
        return routeInfo;
    }
}
