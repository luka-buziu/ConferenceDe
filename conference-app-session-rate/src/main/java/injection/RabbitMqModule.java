package injection;


import dagger.Binds;
import dagger.Module;
import rabbitMq.RabbitMq;
import rabbitMq.RabbitMqImpl;

@Module
public abstract class RabbitMqModule {

    @Binds
    abstract RabbitMq rabbitMq(RabbitMqImpl rabbitMqImp);
}
