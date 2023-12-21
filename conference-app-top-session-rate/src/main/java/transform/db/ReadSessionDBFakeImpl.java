package transform.db;

import model.Sessions;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;
import transform.StringToSessionFn;

public class ReadSessionDBFakeImpl extends ReadSessionDB{
    @Override
    public PCollection<Sessions> expand(PBegin input) {
        return input.apply(TextIO.read().from("src/main/resources/fakedata/fake_session_data.txt"))
                .apply(MapElements.via(new StringToSessionFn()));
    }


}
