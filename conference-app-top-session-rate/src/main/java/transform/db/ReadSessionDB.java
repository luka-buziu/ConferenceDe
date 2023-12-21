package transform.db;

import model.Sessions;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public abstract class ReadSessionDB extends PTransform<PBegin, PCollection<Sessions>> implements Serializable {
}
