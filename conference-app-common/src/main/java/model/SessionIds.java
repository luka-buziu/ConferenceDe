package model;

import java.io.Serializable;

public class SessionIds implements Serializable {
    private String id;

    public SessionIds() {
    }

    public SessionIds(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SessionIds{" +
                "id='" + id + '\'' +
                "dwsad"+
                '}';
    }
}
