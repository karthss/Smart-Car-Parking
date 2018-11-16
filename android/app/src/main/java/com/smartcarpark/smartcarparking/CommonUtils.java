package com.smartcarpark.smartcarparking;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    private static final CommonUtils ourInstance = new CommonUtils();

    public static CommonUtils getInstance() {
        return ourInstance;
    }

    private CommonUtils() {
    }

    public String getJsonString(String string) {
        Pattern pattern = Pattern.compile(".*(\\{.*\\})");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
