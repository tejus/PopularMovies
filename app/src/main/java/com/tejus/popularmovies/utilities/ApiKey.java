package com.tejus.popularmovies.utilities;

public class ApiKey {

    private static String apiKey = null;

    private static boolean apiSet = false;

    public static String getApiKey() {
        return apiKey;
    }

    public static boolean isApiSet() {
        return apiSet;
    }

    public static void setApiKey(String api) {
        apiKey = api;
        apiSet = true;
    }

    public static void clearApiKey () {
        apiKey = null;
        apiSet = false;
    }
}
