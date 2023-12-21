package common.db;

import common.CollectIdList;
import common.utils.BeamUtils;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.Combine;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CollectIdListTest implements Serializable {

    @Test
    public void collectIdListTest() {
        Pipeline pipeline = BeamUtils.createPipeline();
        PCollection<List<Integer>> stringList = pipeline.
                apply(Create.of("1", "2", "3"))
                .apply(Combine.globally(new CollectIdList())).apply(MapElements.via(new SimpleFunction<List<Integer>, List<Integer>>() {
                    @Override
                    public List<Integer> apply(List<Integer> input) {
                        return input.stream().sorted().collect(Collectors.toList());
                    }
                }));

        List<Integer> integers = List.of(1, 2, 3);
        PAssert.that(stringList).containsInAnyOrder(integers);
        pipeline.run().waitUntilFinish();

    }

}
