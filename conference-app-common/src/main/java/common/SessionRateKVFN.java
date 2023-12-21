package common;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.io.Serializable;
import java.util.Objects;

public class SessionRateKVFN extends DoFn<String, KV<Integer, Integer>> implements Serializable {
    @ProcessElement
    public void processElement(ProcessContext c) {
        String[] data = Objects.requireNonNull(c.element()).split(",");
        if (data.length >= 3) {
            int sessionId = Integer.parseInt(data[1]);
            int rating = Integer.parseInt(data[2]);
            c.output(KV.of(sessionId, rating));
        }
    }
}