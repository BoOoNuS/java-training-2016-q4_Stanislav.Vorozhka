package ua.nure.vorozhka.SummaryTask4.db.model.constant;

import java.io.Serializable;

/**
 * Created by Stanislav on 05.01.2017.
 */
public enum PlaceType implements Serializable {

    COUPE("coupe"), RESERVED_SEAT("reserved seat"), TOTAL("the total");

    private String name;

    PlaceType(String name) {
        this.name = name;
    }

    public static PlaceType getPlaceType(int placeTypeId) {
        return PlaceType.values()[--placeTypeId];
    }

    public String getName() {
        return name;
    }
}
