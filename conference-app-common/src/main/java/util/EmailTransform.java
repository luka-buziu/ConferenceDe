package util;

import common.db.JdbcConnector;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

@Singleton
public class EmailTransform implements Serializable {

    @Inject
    public EmailTransform() {
    }

    public String mailTransform(String stringToReplace, String replacement, String path) {
        String text = new Scanner(Objects.requireNonNull(JdbcConnector.class.getResourceAsStream(path)), StandardCharsets.UTF_8).useDelimiter("\\A").next();
        return Arrays.stream(text.split("\n")).map(line -> {
            if (line.contains(stringToReplace)) {
                return line.replace(stringToReplace, replacement);
            } else {
                return line;
            }
        }).collect(Collectors.joining("\n"));

    }
}