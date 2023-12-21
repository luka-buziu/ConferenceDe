package common.db.read_db;

import common.utils.BeamUtils;
import model.Participants;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Test;

public class ReadParticipantDBFakeImplTest {
    @Test
    public void testReadParticipantDB() {
        Pipeline pipeline = BeamUtils.createPipeline();
        PCollection<Participants> participantsPCollection = pipeline.apply(new ReadParticipantDBFakeImpl());
        PAssert.that(participantsPCollection).containsInAnyOrder(new Participants("Fernando","Bahringer","omjt30@gmail.com"),
                new Participants("Freddy","Reilly","afky82@gmail.com"),
                new Participants("Toya","Robel","jnim74@gmail.com"));
    }

}
