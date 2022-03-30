package pl.nethos.rekrutacja.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatyzatorDaty {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime toLocalDateTime(String requestDateTimeStr) {

        return LocalDateTime.parse(requestDateTimeStr.substring(7, 11) +
                "-" + requestDateTimeStr.substring(4, 6) +
                "-" + requestDateTimeStr.substring(1,3) +
                " " + requestDateTimeStr.substring(12, requestDateTimeStr.length()-1), formatter);
    }


}
