package model;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Sessions implements Serializable {
    private int id;
    private String sessionTitle;
    private double sessionRate;
    private List<Speaker> speakers;

    @Inject
    public Sessions(int id, String sessionTitle, double sessionRate) {
        this.id = id;
        this.sessionTitle = sessionTitle;
        this.sessionRate = sessionRate;
        this.speakers =new ArrayList<>();
    }


    public Sessions(int id, String sessionTitle, double sessionRate, List<Speaker> speakers) {
        this.id = id;
        this.sessionTitle = sessionTitle;
        this.sessionRate = sessionRate;
        this.speakers = speakers;
    }

    @Override
    public String toString() {
        return
                "sessionTitle='" + sessionTitle + '\'' +
                ", presented by " + speakers.stream().map(Speaker::toString).collect(Collectors.joining(", "))  ;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public double getSessionRate() {
        return sessionRate;
    }

    public void setSessionRate(double sessionRate) {
        this.sessionRate = sessionRate;
    }

    public List<Speaker> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Speaker> speakers) {
        this.speakers = speakers;
    }
}
