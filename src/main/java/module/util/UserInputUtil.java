package module.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

public class UserInputUtil {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static double doubleValue() throws IOException {
        String value = READER.readLine();
        return Double.parseDouble(value);
    }

    public static int intValue() throws IOException {
        String value = READER.readLine();
        return Integer.parseInt(value);
    }

    public static String stringValue() throws IOException {
        return READER.readLine();
    }

    public static LocalDateTime dateValue() throws IOException {
        return LocalDateTime.parse(READER.readLine());
    }
}
