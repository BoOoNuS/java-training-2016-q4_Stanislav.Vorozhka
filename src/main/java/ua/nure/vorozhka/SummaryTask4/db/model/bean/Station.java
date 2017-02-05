package ua.nure.vorozhka.SummaryTask4.db.model.bean;

import java.io.Serializable;

/**
 * Created by Stanislav on 06.01.2017.
 */
public class Station implements Serializable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "id - " + id +
                ", name - " + name;
    }
}
