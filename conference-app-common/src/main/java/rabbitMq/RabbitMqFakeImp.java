package rabbitMq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.MessageConverter;
import model.SessionIds;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.rabbitmq.RabbitMqMessage;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TimestampedValue;
import org.joda.time.Duration;
import org.joda.time.Instant;

import javax.inject.Inject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RabbitMqFakeImp implements RabbitMq {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static Instant NOW = Instant.EPOCH;

    @Inject
    public RabbitMqFakeImp() {
    }

    @Override
    public PCollection<String> readFromRabbitMq(Pipeline pipeline, String queueName) {
        List<TimestampedValue<byte[]>> timestampedValues = fakeRabbitMqMessage(fakeSessionId());
        return pipeline.apply(Create.of(timestampedValues))
                .apply("Transform", ParDo.of(new DoFn<TimestampedValue<byte[]>, RabbitMqMessage>() {
                    @ProcessElement
                    public void process(ProcessContext context) {
                        byte[] value = context.element().getValue();

                        Date timestamp = context.element().getTimestamp().toDate();

                        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage("", value, null, null,
                                new HashMap(), 1, 1, null, null, null, null,
                                timestamp,
                                null, null, null, null);
                        context.output(rabbitMqMessage);
                    }
                })).apply(MapElements.via(new MessageConverter(objectMapper)))
                .apply(WithTimestamps.of((SerializableFunction<TimestampedValue<String>, Instant>)
                        input -> new Instant(input.getTimestamp())))
                .apply(ParDo.of(new DoFn<TimestampedValue<String>, String>() {
                    @ProcessElement
                    public void process(ProcessContext context){
                        context.output(Objects.requireNonNull(context.element()).getValue());
                    }
                }))
                .apply(Window.into(FixedWindows.of(Duration.standardSeconds(5L))));
    }

    public List<TimestampedValue<byte[]>> fakeRabbitMqMessage(List<SessionIds> sessionIds) {

        List<String> collect = sessionIds.stream().map(sessionRates -> {
                    try {
                        return objectMapper.writeValueAsString(sessionRates);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());


        return IntStream.range(0, collect.size()).mapToObj(count ->
                        TimestampedValue.of(collect.get(count).getBytes(), NOW.plus(Duration.standardSeconds(count))))
                .collect(Collectors.toList());


    }

    public List<SessionIds> fakeSessionId() {

        SessionIds sessionId = new SessionIds("21");
        SessionIds sessionIds1 = new SessionIds("23");
        SessionIds sessionIds2 = new SessionIds("22");
        SessionIds sessionIds3 = new SessionIds("24");
        SessionIds sessionIds4 = new SessionIds("22");
        SessionIds sessionIds5 = new SessionIds("23");
        return List.of(sessionId, sessionIds2, sessionIds3,
                sessionIds4, sessionIds1, sessionIds5);
    }

}
