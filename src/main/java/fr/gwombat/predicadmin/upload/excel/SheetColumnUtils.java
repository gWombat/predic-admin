package fr.gwombat.predicadmin.upload.excel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SheetColumnUtils {

    private static final Logger         logger          = LoggerFactory.getLogger(SheetColumnUtils.class);

    private static final char[]         classicAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static Map<String, Integer> mapValues       = new HashMap<>(0);

    static {
        int counter = 0;
        for (int i = 0; i < classicAlphabet.length; i++)
            mapValues.put(String.valueOf(classicAlphabet[i]), counter++);

        for (int i = 0; i < classicAlphabet.length; i++) {
            for (int j = 0; j < classicAlphabet.length; j++)
                mapValues.put(String.valueOf(classicAlphabet[i]) + String.valueOf(classicAlphabet[j]), counter++);
        }

        logger.debug("Number of columns: {}", mapValues.size());
    }

    private SheetColumnUtils() {
    }

    public static int getValueOfColumn(final String column) {
        if (column != null)
            return mapValues.get(column.toUpperCase());
        return -1;
    }

    public static List<String> getAllColumns() {
        final List<String> columns = mapValues.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        columns.sort((c1, c2) -> {
            if(c1.length() < c2.length())
                return -1;
            if(c1.length() == c2.length())
                return c1.compareTo(c2);
            return 1;
        });
        return Collections.unmodifiableList(columns);
    }

}
