package ua.nure.vorozhka.SummaryTask4.db.model.constant;

import java.io.Serializable;

/**
 * Created by Stanislav on 09.01.2017.
 */
public enum Role implements Serializable {

    ADMIN, CLIENT;

    public static Role getRole(int roleId) {
        return Role.values()[--roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
