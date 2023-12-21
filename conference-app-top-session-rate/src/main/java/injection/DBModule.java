package injection;

import common.db.read_db.ReadParticipantDB;
import common.db.read_db.ReadParticipantDBImpl;
import dagger.Binds;
import dagger.Module;
import transform.db.ReadSessionDB;
import transform.db.ReadSessionDBImpl;

@Module
public abstract class DBModule {

    @Binds
    abstract ReadParticipantDB readParticipantDB(ReadParticipantDBImpl readParticipantDB);

    @Binds
    abstract ReadSessionDB readSessionDB(ReadSessionDBImpl readSessionDB);
}
