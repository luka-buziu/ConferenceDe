package model;

import lombok.Data;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.Objects;

@Data
public class Speaker implements Serializable {

    private String speakerFirstName;
    private String speakerLastName;

    @Inject
    public Speaker(String speakerFirstName, String speakerLastName) {
        this.speakerFirstName = speakerFirstName;
        this.speakerLastName = speakerLastName;
    }

    @Override
    public String toString() {
        return speakerFirstName + ' ' + speakerLastName;
    }

    public String getSpeakerLastName() {
        return this.speakerLastName;
    }

    public String getSpeakerFirstName() {
        return this.speakerFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(speakerFirstName, speaker.speakerFirstName) && Objects.equals(speakerLastName, speaker.speakerLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speakerFirstName, speakerLastName);
    }
}
