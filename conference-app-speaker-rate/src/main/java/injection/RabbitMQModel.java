package injection;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import rabbitMq.RabbitMq;
import rabbitMq.RabbitMqImpl;

import javax.inject.Singleton;

@Module
public abstract class RabbitMQModel {
    @Binds
    abstract RabbitMq rabbitMq(RabbitMqImpl rabbitMqImp);


}
