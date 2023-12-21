package injection;

import application.SpeakerRatePipelineComposer;
import dagger.Component;

@Component(modules = {
        DBModule.class,
        RabbitMQModel.class

})
public interface SpeakerRateMetaDataComponent {

    SpeakerRatePipelineComposer composer();
}
