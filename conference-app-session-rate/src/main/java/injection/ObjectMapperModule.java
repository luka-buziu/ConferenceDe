package injection;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
public  class ObjectMapperModule {

    @Provides
    ObjectMapper provideObjectMapper(){
        return new ObjectMapper();
    }
}
