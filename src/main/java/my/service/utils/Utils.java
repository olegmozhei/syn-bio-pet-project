package my.service.utils;

import org.json.JSONObject;

public class Utils {

    public static void validateJson(JSONObject jsonToValidate, String[] requiredKeys){
        for (String key: requiredKeys) {
            if (!jsonToValidate.has(key)) throw new RuntimeException("Key '" + key + "' not found");
        }
    }
}
