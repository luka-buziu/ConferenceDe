package rabbitMq;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.MessageConverter;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.rabbitmq.RabbitMqIO;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TimestampedValue;
import org.joda.time.Duration;
import org.joda.time.Instant;

import javax.inject.Inject;

public class RabbitMqImpl implements RabbitMq {
    private ObjectMapper objectMapper;
    @Inject
    public RabbitMqImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }



    @Override
    public PCollection<String> readFromRabbitMq(Pipeline pipeline, String queueName) {
       return pipeline.apply(RabbitMqIO.read()
                        .withUri("amqp://guest:guest@127.0.0.1:5672")
                        .withQueue(queueName))
                .apply(MapElements.via(new MessageConverter(objectMapper)))
                .apply(WithTimestamps.of((SerializableFunction<TimestampedValue<String>, Instant>)
                        input -> new Instant(input.getTimestamp())))
                .apply(ParDo.of(new DoFn<TimestampedValue<String>, String>() {
                    @ProcessElement
                    public void process(ProcessContext context) {
                        context.output(context.element().getValue());
                    }
                }))
                .apply(Window.into(FixedWindows.of(Duration.standardSeconds(5L))));



    }
}
