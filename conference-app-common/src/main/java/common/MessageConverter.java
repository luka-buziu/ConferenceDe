package common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.SessionIds;
import org.apache.beam.sdk.io.rabbitmq.RabbitMqMessage;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.TimestampedValue;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MessageConverter extends SimpleFunction<RabbitMqMessage, TimestampedValue<String>> {
    Logger logger= LoggerFactory.getLogger(MessageConverter.class);

    private final ObjectMapper objectMapper;

    public MessageConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public TimestampedValue<String> apply(RabbitMqMessage rabbitMqMessage) {
        String data = new String(rabbitMqMessage.getBody(), StandardCharsets.UTF_8);

        SessionIds dateType = null;
        try {
            dateType = objectMapper.readValue(data, SessionIds.class);
            logger.info(dateType.getId());
            logger.info(TimestampedValue.of(Objects.requireNonNull(dateType).getId(),
                    Instant.ofEpochSecond(rabbitMqMessage.getTimestamp().toInstant().getEpochSecond())).getTimestamp() + ":"
                    + TimestampedValue.of(Objects.requireNonNull(dateType).getId(),
                    Instant.ofEpochSecond(rabbitMqMessage.getTimestamp().toInstant().getEpochSecond())).getValue());
            Instant instant = Instant.ofEpochSecond(rabbitMqMessage
                    .getTimestamp().toInstant().getEpochSecond());


            return TimestampedValue.of(Objects.requireNonNull(dateType).getId(),instant);
        } catch (JsonProcessingException e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            logger.error("The json could not be parsed", stackTrace);
        }

        return TimestampedValue.of("1s",Instant.now());

    }

}
