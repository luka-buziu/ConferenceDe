package common.db.read_db;



import common.utils.BeamUtils;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Test;

import java.util.List;

public class ReadAllFRDBFakeImplTest {

    @Test
    public void testReadAll(){
        Pipeline pipeline = BeamUtils.createPipeline();

        PCollection<String> apply = pipeline.apply(Create.<List<Integer>>of(List.of(1, 2, 3)))
                .apply(new ReadAllFRDBFakeImpl());

        PAssert.that(apply).containsInAnyOrder("26,24,4",
                "27,23,3","28,21,1","29,23,4","30,21,3","33,22,2"
        );

        pipeline.run().waitUntilFinish();

    }
}