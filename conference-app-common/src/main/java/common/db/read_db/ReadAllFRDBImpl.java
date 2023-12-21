package common.db.read_db;

import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.PCollection;

import javax.inject.Inject;
import java.sql.Array;
import java.util.List;

public class ReadAllFRDBImpl extends ReadAllFRDB {
    private final String query;
    @Inject
    public ReadAllFRDBImpl(String query) {
        this.query=query;
    }
    @Override
    public PCollection<String> expand(PCollection<List<Integer>> input) {
        return input.apply(JdbcIO.<List<Integer>, String>readAll()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration
                        .create("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/postgres")
                        .withUsername("postgres")
                        .withPassword("Shanti2022"))
                .withQuery(query)
                .withParameterSetter(((element, preparedStatement) -> {
                    Integer[] objects = element.toArray(new Integer[0]);
                    Array integerArray = preparedStatement.getConnection().createArrayOf("integer", objects);
                    preparedStatement.setArray(1, integerArray);
                }))
                .withRowMapper((JdbcIO.RowMapper<String>) resultSet ->
                        resultSet.getInt(1) + "," + resultSet.getInt(2)
                                + "," + resultSet.getInt(3))
                .withCoder(StringUtf8Coder.of()));
    }
}
