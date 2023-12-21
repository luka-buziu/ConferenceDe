package transform;

import model.Sessions;
import model.Speaker;
import org.apache.beam.repackaged.core.org.apache.commons.lang3.SerializationUtils;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public  class SessionWithSpeakersFn extends DoFn<KV<Integer, Iterable<Sessions>>, Sessions> implements Serializable {
    @ProcessElement
    public void processElement(ProcessContext c) {
        Iterable<Sessions> sessionIterable = Objects.requireNonNull(c.element()).getValue();

        Sessions sessions = SerializationUtils.clone(sessionIterable.iterator().next());
        List<Speaker> speakers = StreamSupport.stream(sessionIterable.spliterator(), false)
                .flatMap(sessions2 -> sessions2.getSpeakers().stream()).collect(Collectors.toList());
        sessions.setSpeakers(speakers);

        c.output(sessions);
    }
}