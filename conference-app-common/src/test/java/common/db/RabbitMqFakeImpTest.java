package common.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.MessageConverter;
import common.utils.BeamUtils;
import model.Participants;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.rabbitmq.RabbitMqMessage;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TimestampedValue;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.junit.Test;
import rabbitMq.RabbitMq;
import rabbitMq.RabbitMqFakeImp;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RabbitMqFakeImpTest implements Serializable {
    private ObjectMapper objectMapper = new ObjectMapper();
    private RabbitMq rabbitMq=new RabbitMqFakeImp();


    @Test
    public void testObjectMapper(){
        Pipeline pipeline = BeamUtils.createPipeline();
        List<TimestampedValue<byte[]>> fakeRabbitMqMessage = fakeRabbitMqMessage(fakeSessionId());

        PCollection<String> transform = pipeline.apply(Create.of(fakeRabbitMqMessage))
                .apply("Transform", ParDo.of(new DoFn<TimestampedValue<byte[]>, RabbitMqMessage>() {
                    @ProcessElement
                    public void process(ProcessContext context) throws IOException {
                        byte[] value = context.element().getValue();
                        RabbitMqMessage rabbitMqMessage = new RabbitMqMessage("", value, null, null,
                                new HashMap(), 1, 1, null, null, null, null, new Date(context.element().getTimestamp().toInstant().getMillis()),
                                null, null, null, null);
                        context.output(rabbitMqMessage);
                    }
                })).apply(MapElements.via(new MessageConverter(objectMapper)))
                .apply(ParDo.of(new DoFn<TimestampedValue<String>, String>() {
                    @ProcessElement
                    public void data(ProcessContext context) {
                        TimestampedValue<String> element = context.element();
                        context.output(context.element().getValue());
                    }
                }));

        PAssert.that(transform).containsInAnyOrder("1s");
        pipeline.run().waitUntilFinish();

    }

    private List<TimestampedValue<byte[]>> fakeRabbitMqMessage(List<Participants> sessionIds) {

        List<String> collect = sessionIds.stream().map(sessionRates -> {
                    try {
                        return objectMapper.writeValueAsString(sessionRates);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return IntStream.range(0, collect.size()).mapToObj(count ->
                        TimestampedValue.of(collect.get(count).getBytes(), Instant.now()
                                .plus(Duration.standardSeconds(count))
                                .toInstant()))
                .collect(Collectors.toList());
    }

    private List<Participants> fakeSessionId() {

        Participants participants=new Participants("dawd","dawawd","fewqaffa");

        return List.of(participants);
    }


}