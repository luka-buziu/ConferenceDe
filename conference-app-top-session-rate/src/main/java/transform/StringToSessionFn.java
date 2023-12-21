package transform;

import model.Sessions;
import model.Speaker;
import org.apache.beam.sdk.transforms.SimpleFunction;

import java.util.ArrayList;
import java.util.List;

public class StringToSessionFn extends SimpleFunction<String, Sessions> {
    @Override
    public Sessions apply(String input) {
        String[] split = input.split(",");
        Speaker speaker=new Speaker(split[3],split[4]);
        List<Speaker> speakers=new ArrayList<>();
        speakers.add(speaker);
        return new Sessions(Integer.parseInt(split[0]),
                split[1],Double.parseDouble(split[2]),speakers);
    }
}

