package injection;


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

    static InsertInDB insertInDB(JdbcConnector jdbcConnector) {
        return new InsertInDBImpl("/sqlScripts/session/sessionUpdate.txt",jdbcConnector);
    }

    @Provides
    static ReadAllFRDB readAllFRDB() {
        return new ReadAllFRDBImpl("SELECT participant_user_id, session_id, rating " +
                "FROM public.participant_session where session_id =ANY(?)");
    }
}
