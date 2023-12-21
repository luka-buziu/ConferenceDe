package transform.db;

import common.db.JdbcConnector;
import model.Sessions;
import model.Speaker;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;

import org.apache.beam.sdk.values.PBegin;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;

public class ReadSessionDBImpl extends ReadSessionDB {
    private  final JdbcConnector jdbcConnector ;

    @Inject
    public ReadSessionDBImpl(JdbcConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
    }

    @Override
    public PCollection<Sessions> expand(PBegin input) {
        return input.apply(jdbcConnector.<Sessions>databaseInit("/sqlScripts/session/SessionRatePostgress.txt")
                .withRowMapper((JdbcIO.RowMapper<Sessions>) resultSet -> {
                    int id = resultSet.getInt(1);
                    String sessionTitle = resultSet.getString(2);
                    double rate = resultSet.getDouble(3);
                    String firstName = resultSet.getString(4);
                    String lastName = resultSet.getString(5);
                    Sessions sessions = new Sessions(id, sessionTitle, rate);
                    sessions.getSpeakers().add(new Speaker(firstName, lastName));
                    return sessions;
                })
                .withCoder(SerializableCoder.of(Sessions.class)));
    }
}