package common.db.read_db;



import common.ConvertToParticipant;
import model.Participants;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

public class ReadParticipantDBFakeImpl extends ReadParticipantDB {
    @Override
    public PCollection<Participants> expand(PBegin input) {
        return input.apply(TextIO.read().from("src/main/resources/fakedata/fake_participant_data.txt"))
                .apply(MapElements.via(new ConvertToParticipant()));
    }
}
