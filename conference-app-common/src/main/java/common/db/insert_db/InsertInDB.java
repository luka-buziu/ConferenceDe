package common.db.insert_db;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;

import java.io.Serializable;

public abstract class InsertInDB extends PTransform<PCollection<KV<Integer, Double>>, PDone> implements Serializable {
}
