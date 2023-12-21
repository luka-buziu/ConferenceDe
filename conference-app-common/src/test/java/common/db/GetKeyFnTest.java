package common.db;

import common.GetKeyFn;
import common.utils.BeamUtils;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Assert;
import org.junit.Test;

public class GetKeyFnTest {
    @Test
    public void testGetKeyFn() {
        Pipeline pipeline = BeamUtils.createPipeline();
        PCollection<Integer> extractedKeys = pipeline.apply(Create.of(KV.of(1, "1"),
                        KV.of(2, "2"), KV.of(3, "3")))
                .apply(MapElements.via(new GetKeyFn<>()));

        PAssert.that(extractedKeys)
                .containsInAnyOrder(1,2,3);
    }


    @Test
    public void apply() {
        GetKeyFn getKeyFn=new GetKeyFn();
        KV<Integer, String> intStringData = KV.of(1, "1");
        Object apply = getKeyFn.apply(intStringData);
        Assert.assertEquals(1, apply);
    }
}
