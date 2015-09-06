package com.nd.hy.android.auto.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nd.hy.android.auto.define.HttpFields;
import com.nd.hy.android.auto.model.Postman;
import com.nd.hy.android.auto.util.FileReadUtil;
import com.nd.hy.android.auto.util.StringHelper;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author liangbx
 * Date 2015/9/1
 * DESC 解析Postman数据
 */
public class PostmanParser {

    public List<Map<String, Object>> parseFile(String filePath) {
        String content = FileReadUtil.readToString(new File(filePath));
        Gson gson = new Gson();
        Postman postman = gson.fromJson(content, new TypeToken<Postman>(){}.getType());
        return getHttpInfoList(postman);
    }

    private List<Map<String, Object>> getHttpInfoList(Postman postman) {
        List<Map<String, Object>> httpInfolist = new ArrayList<>();
        for(int i=0; i<postman.requests.size(); i++) {
            Map<String, Object> httpInfo = new HashMap<>();
            httpInfolist.add(httpInfo);

            Map<String, Object> reqInfo = new HashMap<>();
            httpInfo.put(HttpFields.REQUEST, reqInfo);
            Postman.Request request = postman.requests.get(i);
            getReqInfo(request, reqInfo);

            Map<String, Object> respInfo = new HashMap<>();
            httpInfo.put(HttpFields.RESPONSE, respInfo);
            if(request.responses.size() > 0) {
                getRespInfo(request.responses.get(0), respInfo);
            }
        }
        return httpInfolist;
    }

    /**
     * 获取请求相关数据
     * @param request
     * @param reqInfo
     */
    private void getReqInfo(Postman.Request request, Map<String, Object> reqInfo) {
        reqInfo.put(HttpFields.REQUEST_PATH, getPath(request.url));
        reqInfo.put(HttpFields.REQUEST_METHOD, request.method);
        reqInfo.put(HttpFields.REQUEST_NAME, request.name);
        reqInfo.put(HttpFields.REQUEST_DESCRIPTION, request.description);
        reqInfo.put(HttpFields.REQUEST_PARAMS, getReqParams(request.url));
        reqInfo.put(HttpFields.REQUEST_FN_NAME, getReqFnName(request.method, request.name));
    }

    /**
     * 获取响应字段信息 主要是获取Model相关字段
     * @param responses
     * @param respInfo
     */
    private void getRespInfo(Postman.Response responses, Map<String, Object> respInfo) {
        respInfo.put(HttpFields.RESPONSE_BODY, responses.text);
    }

    private String getPath(String url) {
        try {
            URI uri = new URI(url);
            return uri.getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Map<String, String>> getReqParams(String url) {
        List<Map<String, String>> paramsList = new ArrayList<>();

        try {
            URI uri = new URI(url);
            String query = uri.getQuery();
            if(query != null && !query.equals("")) {
                String[] params = query.split("&");
                for(int i=0; i<params.length; i++) {
                    Map<String, String> paramsMap = new HashMap<>();
                    paramsList.add(paramsMap);
                    String name = params[i].substring(0, params[i].indexOf("="));
                    String value = params[i].substring(params[i].indexOf("=")+1);
                    paramsMap.put("FieldsType", "Query");
                    paramsMap.put("Name", name);
                    paramsMap.put("Type", getParamsType(value));
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return paramsList;
    }

    private String getParamsType(String value) {
        String dataType = "String";
        try {
            Integer.valueOf(value);
            dataType = "int";
        } catch (NumberFormatException e) {
        }

        if("true".equals(value) || "false".equals(value)) {
            dataType = "boolean";
        }

        return dataType;
    }

    private String getReqFnName(String method, String reqName) {
        return method.toLowerCase() + StringHelper.upperCaseFirstLetter(reqName);
    }
}
