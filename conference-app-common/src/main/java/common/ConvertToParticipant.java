package common;


import model.Participants;
import org.apache.beam.sdk.transforms.SimpleFunction;

public class ConvertToParticipant extends SimpleFunction<String, Participants> {
    @Override
    public Participants apply(String input) {
        String[] split = input.split(",");
        return new Participants(split[0], split[1],split[2]);
    }
}

