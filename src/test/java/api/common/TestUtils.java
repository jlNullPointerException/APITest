package api.common;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class TestUtils {
    public static Map<String, String> actualColorMapping = Map.ofEntries(
            Map.entry("cerulean", "#98B2D1"),
            Map.entry("fuchsia rose", "#C74375"),
            Map.entry("true red", "#BF1932"),
            Map.entry("aqua sky", "#7BC4C4"),
            Map.entry("tigerlily", "#E2583E"),
            Map.entry("blue turquoise", "#53B0AE"),
            Map.entry("sand dollar", "#DECDBE"),
            Map.entry("chili pepper", "#9B1B30"),
            Map.entry("blue iris", "#5A5B9F"),
            Map.entry("mimosa", "#F0C05A"),
            Map.entry("turquoise", "#45B5AA"),
            Map.entry("honeysuckle", "#D94F70")
    );

    public static OffsetDateTime roundMinuteDateTime(String time) {
        OffsetDateTime odt = OffsetDateTime.parse(time);

        if (odt.getSecond() >= 30 || (odt.getSecond() == 29 && odt.getNano() > 0)) {
            odt = odt.plusMinutes(1);
        }

        return odt.truncatedTo(ChronoUnit.MINUTES);
    }

}
