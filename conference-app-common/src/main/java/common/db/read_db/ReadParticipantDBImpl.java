package common.db.read_db;


import common.db.JdbcConnector;
import model.Participants;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;


public class ReadParticipantDBImpl extends ReadParticipantDB {
    private final JdbcConnector jdbcConnector;

    @Inject
    public ReadParticipantDBImpl(JdbcConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
    }

    @Override
    public PCollection<Participants> expand(PBegin input) {
        return input.apply(jdbcConnector.<Participants>databaseInit("/sqlScripts/session/TopSessionForParticipant.txt")
                .withRowMapper(resultSet -> {
                    String firstName = resultSet.getString(1);
                    String lastName = resultSet.getString(2);
                    String email = resultSet.getString(3);
                    return new Participants(firstName, lastName, email);
                })
                .withCoder(SerializableCoder.of(Participants.class))
        );
    }
}
