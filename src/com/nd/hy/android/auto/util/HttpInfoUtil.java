package com.nd.hy.android.auto.util;

import com.nd.hy.android.auto.define.HttpFields;
import com.nd.hy.android.auto.define.TmplCommon;
import com.nd.hy.android.auto.define.TmplModel;
import com.nd.hy.android.auto.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author liangbx
 * Date 2015/9/9
 * 负责将 http map数据转换成 bean
 */
public class HttpInfoUtil {

    public List<HttpInfo> mapToBean(List<Map<String, Object>> list) {
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

    public List<Map<String, Object>> beanToMap(List<HttpInfo> httpInfoList) {
        List<Map<String, Object>> mapList = new ArrayList<>();

        for(HttpInfo httpInfo : httpInfoList) {
            Map<String, Object> map = new HashMap<>();
            map.put(HttpFields.REQUEST, getReqMap(httpInfo.getRequest()));
            map.put(HttpFields.RESPONSE, getRespMap(httpInfo.getResponse()));
            mapList.add(map);
        }

        return mapList;
    }

    public Map<String, Object> getReqMap(Request request) {
        Map<String, Object> reqMap = new HashMap<>();

        reqMap.put(HttpFields.REQUEST_PATH, request.getReqPath());
        reqMap.put(HttpFields.REQUEST_METHOD, request.getReqMethod());
        reqMap.put(HttpFields.REQUEST_NAME, request.getReqName());
        reqMap.put(HttpFields.REQUEST_DESCRIPTION, request.getReqDescription());
        reqMap.put(HttpFields.REQUEST_PARAMS, getReqParams(request.getRequestParamList()));
        reqMap.put(HttpFields.REQUEST_FN_NAME, request.getReqFnName());

        return reqMap;
    }

    public List<Map<String, String>> getReqParams(List<RequestParam> paramList) {
        List<Map<String, String>> mapList = new ArrayList<>();

        for(RequestParam reqParam : paramList) {
            Map<String, String> map = new HashMap<>();
            mapList.add(map);

            map.put(HttpFields.REQUEST_PARAMS_TYPE_FOR_URL, reqParam.getTypeForUrl());
            map.put(HttpFields.REQUEST_PARAMS_NAME_FOR_FN, reqParam.getNameForFn());
            map.put(HttpFields.REQUEST_PARAMS_NAME_FOR_URL, reqParam.getNameForUrl());
            map.put(HttpFields.REQUEST_PARAMS_DATA_TYPE, reqParam.getDataType());
        }

        return mapList;
    }

    public Map<String, Object> getRespMap(Response response) {
        Map<String, Object> resqMap = new HashMap<>();
        resqMap.put(HttpFields.RESPONSE_BODY, getModelMap(response.getModel()));
        return resqMap;
    }

    public Map<String, Object> getModelMap(Model model) {
        Map<String, Object> map = new HashMap<>();

        map.put(TmplModel.MODEL_NAME, model.getModelName());
        map.put(TmplCommon.IMPORT_LIST, model.getImportList());

        List<Map<String, Object>> mapList = new ArrayList<>();
        map.put(TmplModel.FIELD_LIST, mapList);

        for(ModelField modelField : model.getModelFieldList()) {
            Map<String, Object> fieldMap = new HashMap<>();
            mapList.add(fieldMap);

            fieldMap.put(TmplModel.FIELD_NAME, modelField.getGenFieldName());
            fieldMap.put(TmplModel.FIELD_RESP_NAME, modelField.getRespFieldName());
            fieldMap.put(TmplModel.FIELD_TYPE, modelField.getDataType());
            fieldMap.put(TmplModel.FN_NAME, StringHelper.upperCaseFirstLetter(modelField.getGenFieldName()));

            if(null != modelField.getSubModel()) {
                fieldMap.put(TmplModel.SUB_CLASS, getModelMap(modelField.getSubModel()));
                fieldMap.put(TmplModel.CLASS_NAME, modelField.getSubModel().getModelName());
            }
        }

        return map;
    }
}
