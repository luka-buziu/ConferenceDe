package common.db;

import common.SessionRateKVFN;
import common.utils.BeamUtils;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Test;

import java.io.Serializable;

public class SessionRateKVFNTest implements Serializable {

    @Test
    public void returnKvValueTest(){
        Pipeline pipeline = BeamUtils.createPipeline();
        PCollection<KV<Integer, Integer>> sessionRateKv = pipeline
                .apply(Create.of("2,21,5", "6,10,2", "3,15,1"))
                .apply(ParDo.of(new SessionRateKVFN()));

        PAssert.that(sessionRateKv).containsInAnyOrder(KV.of(21,5),KV.of(10,2),KV.of(15,1));
        pipeline.run();

    }

    @Test
    public void returnEmptyPCollection(){
        Pipeline pipeline = BeamUtils.createPipeline();
        PCollection<KV<Integer, Integer>> sessionRateKv = pipeline
                .apply(Create.of("2,21", "6,2", "15,1"))
                .apply(ParDo.of(new SessionRateKVFN()));

        PAssert.that(sessionRateKv).containsInAnyOrder();
        pipeline.run();
    }

}