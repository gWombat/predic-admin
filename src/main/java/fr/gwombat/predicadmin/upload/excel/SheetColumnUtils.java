package fr.gwombat.predicadmin.upload.excel;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SheetColumnUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(SheetColumnUtils.class);
    
    private static final char[] classicAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static Map<String, Integer> mapValues = new HashMap<>(0);
    
    static {
        int counter = 0;
        for(int i = 0; i < classicAlphabet.length; i++)
            mapValues.put(String.valueOf(classicAlphabet[i]), counter++);
        
        for(int i = 0; i < classicAlphabet.length; i++){
            for(int j = 0; j < classicAlphabet.length; j++)
                mapValues.put(String.valueOf(classicAlphabet[i]) + String.valueOf(classicAlphabet[j]), counter++);
        }
        
        logger.debug("Number of columns: {}", mapValues.size());
    }
    
    private SheetColumnUtils() {
    }
    
    public static int getValueOfColumn(final String column){
        if(column != null)
            return mapValues.get(column.toUpperCase());
        return -1;
    }

}
