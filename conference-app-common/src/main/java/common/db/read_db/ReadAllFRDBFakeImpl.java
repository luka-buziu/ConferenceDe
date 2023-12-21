package common.db.read_db;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class ReadAllFRDBFakeImpl extends ReadAllFRDB {
    @Inject
    public ReadAllFRDBFakeImpl() {
    }

    @Override
    public PCollection<String> expand(PCollection<List<Integer>> input) {
            return input
                    .apply(ParDo.of(new DoFn<List<Integer>, String>() {
                @ProcessElement
                public void process(ProcessContext  context){
                    try (Stream<String> lines = Files.readAllLines(Path.of("src/main/resources/fakedata/fake_rating_data.txt")).stream()){

                        lines.forEach(context::output);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
    }
}
