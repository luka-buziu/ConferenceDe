package common.db.read_db;

import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;
import java.util.List;

public abstract class ReadAllFRDB  extends PTransform<PCollection<List<Integer>>, PCollection<String>> implements Serializable {
}
