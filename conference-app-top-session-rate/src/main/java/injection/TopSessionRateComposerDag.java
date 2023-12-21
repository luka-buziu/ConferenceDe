package injection;

import application.TopSessionRateComposer;
import dagger.Component;

@Component(modules = {DBModule.class,
        MailModule.class})
public interface TopSessionRateComposerDag {
    TopSessionRateComposer composer();
}
