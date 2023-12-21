package injection;

import application.SessionRatingJob;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component (modules = {ObjectMapperModule.class,
        DBModule.class,
        RabbitMqModule.class} )
public interface SessionRatingMetaDataComponent {
    SessionRatingJob composer();
}
