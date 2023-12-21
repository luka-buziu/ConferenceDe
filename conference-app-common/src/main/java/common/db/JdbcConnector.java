package common.db;

import org.apache.beam.sdk.io.jdbc.JdbcIO;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

@Singleton
public class JdbcConnector {

    @Inject
    public JdbcConnector() {
    }

    public <T> JdbcIO.Read<T> databaseInit(String path) {
        String text = new Scanner(Objects.requireNonNull(JdbcConnector.class.getResourceAsStream(path)), StandardCharsets.UTF_8).useDelimiter("\\A").next();
        System.out.println(text);
        return JdbcIO.<T>read()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration
                        .create("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/postgres")
                        .withUsername("postgres")
                        .withPassword("Shanti2022"))
                .withQuery(text);

    }

    public <T> JdbcIO.Write<T> databaseWrite(String path) {
        String text = new Scanner(Objects.requireNonNull(JdbcConnector.class.getResourceAsStream(path)), StandardCharsets.UTF_8).useDelimiter("\\A").next();
        return JdbcIO.<T>write()
                .withDataSourceConfiguration(JdbcIO.DataSourceConfiguration
                        .create("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/postgres")
                        .withUsername("postgres")
                        .withPassword("Shanti2022"))
                .withStatement(text);
    }


}
