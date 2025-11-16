package domain;

import java.util.HashMap;
import java.util.Map;

public class ParserJSON {

    public static Map<String, String> parse(String json) {
        Map<String, String> map = new HashMap<>();

        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        
        String[] pairs = json.split(",");

        for (String pair : pairs) {
            String[] kv = pair.split(":", 2);
            if (kv.length != 2) continue;

            String key = clean(kv[0]);
            String value = clean(kv[1]);

            map.put(key, value);
        }
        
        return map;
    }

    private static String clean(String s) {
        return s.trim()
                .replace("\"", "")   
                .trim();
    }
}
