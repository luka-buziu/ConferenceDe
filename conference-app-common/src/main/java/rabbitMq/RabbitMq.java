package rabbitMq;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.rabbitmq.RabbitMqMessage;
import org.apache.beam.sdk.values.PCollection;

import java.io.Serializable;

public interface RabbitMq  extends Serializable {
   PCollection<String> readFromRabbitMq(Pipeline pipeline,String queueName);
}
