package common.db.read_db;

import model.Participants;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public abstract class ReadParticipantDB extends PTransform<PBegin, PCollection<Participants>> implements Serializable{

}
