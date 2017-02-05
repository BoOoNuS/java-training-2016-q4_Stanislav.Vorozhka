package ua.nure.vorozhka.SummaryTask4.db.model.constant;

import java.io.Serializable;

/**
 * Created by Stanislav on 05.01.2017.
 */
public enum StationType implements Serializable {

    INITIAL, TERMINAL, WAY;

    public static StationType getStationType(int routeTypeId){
        return StationType.values()[--routeTypeId];
    }

    public String getName(){
        return name().toLowerCase();
    }
}
