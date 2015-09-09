package com.nd.hy.android.auto.parser;

import com.nd.hy.android.auto.model.HttpInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author liangbx
 * Date 2015/9/1
 */
public class HttpParseFactory {

    public Map<String, Object> parseRequest() {

        Map<String, Object> requInfo = new HashMap<>();

        return requInfo;
    }

    public Map<String, Object> parseResponse() {

        Map<String, Object> respInfo = new HashMap<>();

        return respInfo;
    }

//    public static List<Map<String, Object>> parsePostman(String filePath) {
//        PostmanParser postmanParser = new PostmanParser();
//        return postmanParser.parseFile(filePath);
//    }

    public static List<HttpInfo> parsePostman(String filePath) {
        PostmanParser postmanParser = new PostmanParser();
        return postmanParser.parserFileToBean(filePath);
    }
}
