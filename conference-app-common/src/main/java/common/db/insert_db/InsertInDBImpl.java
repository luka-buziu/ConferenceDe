package common.db.insert_db;


import common.db.JdbcConnector;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PDone;

public class InsertInDBImpl extends InsertInDB {
    private  final JdbcConnector jdbcConnector;
    private final String path;

    public InsertInDBImpl( String path,JdbcConnector jdbcConnector) {
        this.jdbcConnector = jdbcConnector;
        this.path = path;
    }

    @Override
    public PDone expand(PCollection<KV<Integer, Double>> input) {
        return input.apply(jdbcConnector.<KV<Integer, Double>>databaseWrite(path)
                .withPreparedStatementSetter((JdbcIO.PreparedStatementSetter<KV<Integer, Double>>) (element, preparedStatement) -> {
                    preparedStatement.setDouble(1, element.getValue());
                    preparedStatement.setInt(2, element.getKey());
                })
        );

    }
}