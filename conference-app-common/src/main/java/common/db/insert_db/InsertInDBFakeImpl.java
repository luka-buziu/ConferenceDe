package common.db.insert_db;

import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;

import javax.inject.Inject;

public class InsertInDBFakeImpl extends InsertInDB {
    private String fileLocation;
    @Inject
    public InsertInDBFakeImpl(String fileLocation) {
        this.fileLocation=fileLocation;
    }

    @Override
    public PDone expand(PCollection<KV<Integer, Double>> input) {
        return input.apply(MapElements.via(new SimpleFunction<KV<Integer, Double>, String>() {
            @Override
            public String apply(KV<Integer, Double> input) {
                return input.getKey() + "," + input.getValue();
            }
        })).apply(TextIO.write()
                .to("src/main/resources/fakedata/"+fileLocation)
                .withNumShards(1)
                .withSuffix(".txt")

        );
    }
}
