package common.db;

import common.CollectDataToList;
import common.utils.BeamUtils;
import model.Speaker;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.Combine;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Test;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CollectDataToListTest implements Serializable {
    @Test
    public void collectList() {
        Pipeline pipeline = BeamUtils.createPipeline();
        List<Speaker> speakers = List.of(new Speaker("luka", "buziu"),
                new Speaker("indrit", "Vaka"));
        PCollection<List<Speaker>> listSpeakers = pipeline.apply(Create.of(speakers))
                .apply(Combine.globally(new CollectDataToList<Speaker>()))
                .apply(MapElements.via(new SimpleFunction<List<Speaker>, List<Speaker>>() {
                    @Override
                    public List<Speaker> apply(List<Speaker> input) {
                        List<Speaker> collect = input.stream()
                                .sorted(Comparator.comparing(Speaker::getSpeakerFirstName))
                                .collect(Collectors.toList());
                        System.out.println(collect);
                        return collect;
                    }
                }));

        List<Speaker> speakers1 = List.of(new Speaker("indrit", "Vaka"),
                new Speaker("luka", "buziu"));
        PAssert.that(listSpeakers).containsInAnyOrder(speakers1);
        pipeline.run().waitUntilFinish();
    }
}
