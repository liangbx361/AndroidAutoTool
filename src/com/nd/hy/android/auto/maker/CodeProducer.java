package com.nd.hy.android.auto.maker;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.define.*;
import com.nd.hy.android.auto.model.HttpInfo;
import com.nd.hy.android.auto.model.Model;
import com.nd.hy.android.auto.model.Response;
import com.nd.hy.android.auto.parser.RequestMethod;
import com.nd.hy.android.auto.util.HttpInfoUtil;
import com.nd.hy.android.auto.view.CustDialog;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

import static com.nd.hy.android.auto.define.TmplModel.CLASS_NAME;

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

    public boolean produceHttpCode(List<HttpInfo> httpInfoList) {
        boolean isComplete = true;

        Map<String, Object> baseInfo = new HashMap<>();
        HttpInfoUtil httpInfoUtil = new HttpInfoUtil();

        String packageName = MainApp.project.getPackageName();
        baseInfo.put("PackageName", packageName);

        File genDir = new File(MainApp.project.getGenPath()+ "/" + packageName.replace(".", "/"));
        if(!genDir.exists()) {
            genDir.mkdirs();
        }

        //生成BaseModel
        if(null != MainApp.project.getBaseModel()) {
            Map<String, Object> baseMap = httpInfoUtil.getModelMap(MainApp.project.getBaseModel());
            baseMap.put(TmplModel.BASE_MODEL_FLAG, "true");
            genModel(baseMap, baseInfo, genDir);
        }

        //生成Action
        List<Map<String, Object>> apiList = new ArrayList<>();
        Set<String> importList = new HashSet<>();
        for(HttpInfo httpInfo : httpInfoList) {
            Response response = httpInfo.getResponse();
            Model model = response.getModel();

            Map<String, Object> modelMap = httpInfoUtil.getModelMap(model);
            genModel(modelMap, baseInfo, genDir);

            Map<String, Object> reqMap = httpInfoUtil.getReqMap(httpInfo.getRequest());
            baseInfo.put(TmplModel.MODEL_NAME, model.getModelName());
            Set<String> actImportList = new HashSet<>();
            genAction(actImportList, (List<Map<String, Object>>) reqMap.get(HttpFields.REQUEST_PARAMS), baseInfo, genDir);

            Map<String, Object> apiInfo = new HashMap<>();
            apiList.add(apiInfo);
            String modelName = (String) baseInfo.get(TmplModel.MODEL_NAME);
            Model baseModel = MainApp.project.getBaseModel();
            importList.add(JavaImport.LIST);
            if(null != baseModel) {
                importList.add(packageName + ".model." + baseModel.getModelName());
            }
            importList.add(packageName + ".model." + modelName);

            if (null != baseModel) {
                if(!model.isListFlag()) {
                    modelName = baseModel.getModelName() + "<" + modelName + ">";
                } else {
                    modelName = baseModel.getModelName() + "<List<" + modelName + ">>";

                }
            }
            apiInfo.put(TmplModel.MODEL_NAME, modelName);
            apiInfo.put(HttpFields.REQUEST_METHOD, reqMap.get(HttpFields.REQUEST_METHOD));
            apiInfo.put(HttpFields.REQUEST_PATH, reqMap.get(HttpFields.REQUEST_PATH));
            apiInfo.put(HttpFields.REQUEST_PARAMS, reqMap.get(HttpFields.REQUEST_PARAMS));
            apiInfo.put(HttpFields.REQUEST_FN_NAME, reqMap.get(HttpFields.REQUEST_FN_NAME));

            //获取生成API相关的Import
            String importPath = getRetrofitImports(reqMap.get(HttpFields.REQUEST_METHOD).toString());
            if(importPath != null && !importPath.equals("")) {
                importList.add(importPath);
            }

            String params = reqMap.get(HttpFields.REQUEST_PARAMS).toString();
            if(params != null && !params.equals("")) {
                importList.add(RetrofitImport.QUERY);
            }

            StringBuilder modelPath = new StringBuilder();
            modelPath.append(baseInfo.get("PackageName").toString());
            modelPath.append(".model.");
            modelPath.append(baseInfo.get(TmplModel.MODEL_NAME).toString());
            importList.add(modelPath.toString());
        }

        genRestApi(importList, apiList, baseInfo, genDir);

        return isComplete;
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
     */
    public void genModel(Map<String, Object> model, Map<String, Object> baseInfo, File genDir) {

        File modelDir = new File(genDir, "model");
        if(!modelDir.exists()) {
            modelDir.mkdirs();
        }

        String modelName = model.get(TmplModel.MODEL_NAME).toString();

        Map<String, Object> map = new HashMap<>(baseInfo);
        if(!model.containsKey(TmplModel.BASE_MODEL_FLAG)) {
            map.put(TmplModel.MODEL_NAME, model.get(TmplModel.MODEL_NAME));
        } else {
            map.put(TmplModel.MODEL_NAME, model.get(TmplModel.MODEL_NAME).toString() + "<T>");
        }
        map.put(TmplModel.FIELD_LIST, model.get(TmplModel.FIELD_LIST));
        map.put(TmplCommon.IMPORT_LIST, model.get(TmplCommon.IMPORT_LIST));

        File modelFile = new File(modelDir, modelName + ".java");
        try {
            Freemarker.getInstance().process(map, "Model.ftl", modelFile);
            genSubModel(model, baseInfo, modelDir, modelName);
        } catch (Exception e) {
            e.printStackTrace();
            CustDialog.showException(e);
        }
    }

    private void genSubModel(Map<String, Object> model, Map<String, Object> baseInfo, File modelDir, String modelName) {

        List<Map<String, Object>> fieldsList = (List<Map<String, Object>>) model.get(TmplModel.FIELD_LIST);

        for(int i=0; i<fieldsList.size(); i++) {

            Map<String, Object> fields = fieldsList.get(i);
            if(fields.containsKey(TmplModel.SUB_CLASS)) {

                Map<String, Object> subModel = (Map<String, Object>) fields.get(TmplModel.SUB_CLASS);

                Map<String, Object> codeData = new HashMap<>(baseInfo);
                codeData.put(TmplModel.MODEL_NAME, fields.get(CLASS_NAME));
                codeData.put(TmplModel.FIELD_LIST, subModel.get(TmplModel.FIELD_LIST));
                codeData.put(TmplCommon.IMPORT_LIST, subModel.get(TmplCommon.IMPORT_LIST));

                File modelFile = new File(modelDir, fields.get(CLASS_NAME) + ".java");
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
    public void genAction(Set<String> importList, List<Map<String, Object>> dataList, Map<String, Object> baseInfo, File genDir){

        File actionDir = new File(genDir, "action");
        if(!actionDir.exists()) {
            actionDir.mkdirs();
        }

        Map<String, Object> map = new HashMap<>(baseInfo);
        map.put(TmplAction.DATA_LIST, dataList);
        map.put(TmplAction.ACTION_NAME, baseInfo.get(TmplModel.MODEL_NAME)+"Action");
        String fnParams = getFnParams(dataList);
        if(!"".equals(fnParams)) {
            map.put(TmplAction.ACTION_PARAMS, getFnParams(dataList));
        }
        map.put(TmplCommon.IMPORT_LIST, importList);

        File actionFile = new File(actionDir, map.get(TmplModel.MODEL_NAME) + "Action.java");
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
            params.append(map.get(HttpFields.REQUEST_PARAMS_DATA_TYPE).toString());
            params.append(" ");
            params.append(map.get(HttpFields.REQUEST_PARAMS_NAME_FOR_FN));
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
        map.put(TmplCommon.IMPORT_LIST, importList);
        map.put(TmplRestApi.API_LIST, restApiList);
        map.put(TmplRestApi.API_PROTOCOL_NAME, "RestApiProtocol");

        File actionFile = new File(apiDir, "RestApiProtocol.java");
        try {
            Freemarker.getInstance().process(map, "RestApi.ftl", actionFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
