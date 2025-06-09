package com.chat.interceptor.utils;

import java.util.HashMap;
import java.util.Map;

public class UrlList {

    private static final Map<String, String[]> authenticatedList;

    static {
        authenticatedList = new HashMap<>();

        String[] authenticatedGetList = {

        };

        authenticatedList.put("GET", authenticatedGetList);
    }

    public static Map<String, String[]> getAuthenticatedList() {
        return authenticatedList;
    }
}
