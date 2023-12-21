package injection;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.db.JdbcConnector;
import common.db.insert_db.InsertInDB;
import common.db.insert_db.InsertInDBImpl;
import common.db.read_db.ReadAllFRDB;
import common.db.read_db.ReadAllFRDBImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    @Provides
    InsertInDB insertInDB(JdbcConnector jdbcConnector){
        return new InsertInDBImpl("/sqlScripts/speaker/UpdateSpeakerRate.txt",jdbcConnector);
    }
    @Provides
    JdbcConnector jdbcConnector(){
        return new JdbcConnector();
    }

    @Provides
    ReadAllFRDB readAllFRDB(){
        return new ReadAllFRDBImpl("SELECT user_id, speaker_id, rating\n" +
                "\tFROM public.speaker_rate where speaker_id =ANY(?)");
    }
    @Provides
    ObjectMapper mapper(){
        return new ObjectMapper();
    }
}
