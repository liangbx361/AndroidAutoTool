package com.nd.hy.android.auto.maker;

import com.nd.hy.android.auto.define.*;
import com.nd.hy.android.auto.parser.*;
import com.nd.hy.android.auto.parser.RequestMethod;

import java.io.File;
import java.util.*;

import static com.nd.hy.android.auto.define.TmplModelFields.CLASS_NAME;

/**
 * Author liangbx
 * Date 2015/9/2
 * DESC 代码生成类
 */
public class CodeProducer {

    private static CodeProducer mCodeProducer;

    private CodeProducer() {

    }

    public static synchronized CodeProducer getInstance() {

        if(mCodeProducer == null) {
            mCodeProducer = new CodeProducer();
        }

        return mCodeProducer;
    }

    public void produceHttpCode(List<Map<String, Object>> httpInfoList) {
        Map<String, Object> baseInfo = new HashMap<>();
        String packageName = "com.nd.hy.android.auto";
        baseInfo.put("PackageName", packageName);

        File genDir = new File("gen/" + packageName.replace(".", "/"));
        if(!genDir.exists()) {
            genDir.mkdirs();
        }

        List<Map<String, Object>> apiList = new ArrayList<>();
        Set<String> importList = new HashSet<>();
        for(Map<String, Object> httpInfo : httpInfoList) {

            Map<String, Object> request = (Map<String, Object>) httpInfo.get(HttpFields.REQUEST);
            Map<String, Object> response = (Map<String, Object>) httpInfo.get(HttpFields.RESPONSE);

            baseInfo.put(TmplModelFields.MODEL_NAME, request.get(HttpFields.REQUEST_NAME));
            genModel(response.get(HttpFields.RESPONSE_BODY).toString(), baseInfo, genDir);

            List<Map<String, Object>> reqParamsList = (List<Map<String, Object>>) request.get(HttpFields.REQUEST_PARAMS);
            genAction(reqParamsList, baseInfo, genDir);

            Map<String, Object> apiInfo = new HashMap<>();
            apiList.add(apiInfo);
            apiInfo.put(TmplModelFields.MODEL_NAME, baseInfo.get(TmplModelFields.MODEL_NAME));
            apiInfo.put(HttpFields.REQUEST_METHOD, request.get(HttpFields.REQUEST_METHOD));
            apiInfo.put(HttpFields.REQUEST_PATH, request.get(HttpFields.REQUEST_PATH));
            apiInfo.put(HttpFields.REQUEST_PARAMS, request.get(HttpFields.REQUEST_PARAMS));
            apiInfo.put(HttpFields.REQUEST_FN_NAME, request.get(HttpFields.REQUEST_FN_NAME));

            //获取生成API相关的Import
            String importPath = getRetrofitImports(request.get(HttpFields.REQUEST_METHOD).toString());
            if(importPath != null && !importPath.equals("")) {
                importList.add(importPath);
            }

            String params = request.get(HttpFields.REQUEST_PARAMS).toString();
            if(params != null && !params.equals("")) {
                importList.add(RetrofitImport.QUERY);
            }

            StringBuilder modelPath = new StringBuilder();
            modelPath.append(baseInfo.get("PackageName").toString());
            modelPath.append(".model.");
            modelPath.append(baseInfo.get(TmplModelFields.MODEL_NAME).toString());
            importList.add(modelPath.toString());
        }

        genRestApi(importList, apiList, baseInfo, genDir);
    }

    public String getRetrofitImports(String requestMethod) {
        String importPath = null;
        if(RequestMethod.GET.equals(requestMethod)) {
            importPath =  RetrofitImport.GET;
        } else if(RequestMethod.POST.equals(requestMethod)) {
            importPath =  RetrofitImport.POST;
        } else if(RequestMethod.PUT.equals(requestMethod)) {
            importPath =  RetrofitImport.PUT;
        } else if(RequestMethod.DELETE.equals(requestMethod)) {
            importPath =  RetrofitImport.DELETE;
        }
        return importPath;
    };

    /**
     * 生成Model
     * @param respBody
     * @param baseInfo
     */
    public void genModel(String respBody, Map<String, Object> baseInfo, File genDir) {

//        File modelDir = new File(genDir, "model");
//        if(!modelDir.exists()) {
//            modelDir.mkdirs();
//        }
//
//        ResponseParser respParser = new JsonRespParser();
//        Map<String, Object> model = respParser.getModelParams(respBody);
//
//        Map<String, Object> map = new HashMap<>(baseInfo);
//        map.put(TmplModelFields.FIELDS_LIST, model.get(TmplModelFields.FIELDS_LIST));
//        map.put(TmplComFields.IMPORT_LIST, model.get(TmplComFields.IMPORT_LIST));
//
//        String modelName = (String) map.get(TmplModelFields.MODEL_NAME);
//        File modelFile = new File(modelDir, modelName + ".java");
//        try {
//            Freemarker.getInstance().process(map, "Model.ftl", modelFile);
//            genSubModel(model, baseInfo, modelDir, modelName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void genSubModel(Map<String, Object> model, Map<String, Object> baseInfo, File modelDir, String modelName) {

        List<Map<String, Object>> fieldsList = (List<Map<String, Object>>) model.get(TmplModelFields.FIELDS_LIST);

        for(int i=0; i<fieldsList.size(); i++) {

            Map<String, Object> fields = fieldsList.get(i);
            if(fields.containsKey(TmplModelFields.SUB_CLASS)) {

                Map<String, Object> subModel = (Map<String, Object>) fields.get(TmplModelFields.SUB_CLASS);

                Map<String, Object> codeData = new HashMap<>(baseInfo);
                codeData.put(TmplModelFields.MODEL_NAME, fields.get(CLASS_NAME));
                codeData.put(TmplModelFields.FIELDS_LIST, subModel.get(TmplModelFields.FIELDS_LIST));
                codeData.put(TmplComFields.IMPORT_LIST, subModel.get(TmplComFields.IMPORT_LIST));

                File modelFile = new File(modelDir, modelName + fields.get(CLASS_NAME) + ".java");
                try {
                    Freemarker.getInstance().process(codeData, "Model.ftl", modelFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                genSubModel(subModel, baseInfo, modelDir, modelName);
            }
        }
    }

    /**
     * 生成Action文件
     * @param dataList
     * @param baseInfo
     */
    public void genAction(List<Map<String, Object>> dataList, Map<String, Object> baseInfo, File genDir){

        File actionDir = new File(genDir, "action");
        if(!actionDir.exists()) {
            actionDir.mkdirs();
        }

        Map<String, Object> map = new HashMap<>(baseInfo);
        map.put("DataList", dataList);
        map.put(TmplActionFields.ACTION_NAME, baseInfo.get(TmplModelFields.MODEL_NAME)+"Action");
        String fnParams = getFnParams(dataList);
        if(!"".equals(fnParams)) {
            map.put("Params", getFnParams(dataList));
        }
        map.put("ImportList", new ArrayList<String>());

        File actionFile = new File(actionDir, map.get(TmplModelFields.MODEL_NAME) + "Action.java");
        try {
            Freemarker.getInstance().process(map, "Action.ftl", actionFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取函数的参数
     * @param dataList
     * @return
     */
    public String getFnParams(List<Map<String, Object>> dataList) {

        StringBuilder params = new StringBuilder();
        for(int i=0; i<dataList.size(); i++) {
            Map<String, Object> map = dataList.get(i);
            params.append(map.get("Type").toString());
            params.append(" ");
            params.append(map.get("Name"));
            if(i < dataList.size()-1) {
                params.append(", ");
            }
        }

        return params.toString();
    }

    /**
     * 生成Api列表
     * @param restApiList
     * @param baseInfo
     */
    public void genRestApi(Set<String> importList, List<Map<String, Object>> restApiList, Map<String, Object> baseInfo, File genDir) {

        File apiDir = new File(genDir, "service/api");
        if(!apiDir.exists()) {
            apiDir.mkdirs();
        }

        Map<String, Object> map = new HashMap<>(baseInfo);
        map.put(TmplComFields.IMPORT_LIST, importList);
        map.put(TmplRestApiFields.API_LIST, restApiList);
        map.put(TmplRestApiFields.API_PROTOCOL_NAME, "RestApiProtocol");

        File actionFile = new File(apiDir, "RestApiProtocol.java");
        try {
            Freemarker.getInstance().process(map, "RestApi.ftl", actionFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
