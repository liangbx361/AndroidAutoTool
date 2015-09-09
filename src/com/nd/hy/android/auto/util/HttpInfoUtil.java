package com.nd.hy.android.auto.util;

import com.nd.hy.android.auto.define.HttpFields;
import com.nd.hy.android.auto.model.HttpInfo;
import com.nd.hy.android.auto.model.Model;
import com.nd.hy.android.auto.model.Request;
import com.nd.hy.android.auto.model.Response;
import com.sun.javafx.sg.prism.NGShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author liangbx
 * Date 2015/9/9
 * 负责将 http map数据转换成 bean
 */
public class HttpInfoUtil {

    public static List<HttpInfo> mapTransToBean(List<Map<String, Object>> list) {
        List<HttpInfo> beanList = new ArrayList<>();

        for(Map<String, Object> map : list) {
            Map<String, Object> requestMap = (Map<String, Object>) map.get(HttpFields.REQUEST);
            Map<String, Object> responseMap = (Map<String, Object>) map.get(HttpFields.RESPONSE);

            HttpInfo httpInfo = new HttpInfo();
            beanList.add(httpInfo);
            Request request = new Request();
            httpInfo.setRequest(request);
            Response response = new Response();
            httpInfo.setResponse(response);

            //Request
            request.setReqMethod(requestMap.get(HttpFields.REQUEST_METHOD).toString());

            //Model 名称
            Model model = new Model();
            model.setModelName(requestMap.get(HttpFields.REQUEST_NAME).toString());


        }

        return beanList;
    };
}
