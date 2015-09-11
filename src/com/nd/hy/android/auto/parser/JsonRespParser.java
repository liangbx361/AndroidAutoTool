package com.nd.hy.android.auto.parser;

import com.nd.hy.android.auto.define.DataType;
import com.nd.hy.android.auto.define.JavaImport;
import com.nd.hy.android.auto.model.Model;
import com.nd.hy.android.auto.model.ModelField;
import com.nd.hy.android.auto.util.StringHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Author liangbx
 * Date 2015/8/25
 */
public class JsonRespParser implements ResponseParser {

    public static final String TEST_RESPONSE = "{\"a\":[1,2,3,4,5,6],\"b\":[{\"b1\":\"1\"},{\"b2\":\"2\"}],\"c\":true,\"d\":10,\"e\":\"e\",\"f\":1.3,\"g\":{\"g1\":[\"g1\",\"g2\"],\"g2\":[{\"g21\":\"g21\",\"g22\":\"g22\"}]}}";

    public static void main(String[] args) {
        JsonRespParser parser = new JsonRespParser();
        parser.getModel("", TEST_RESPONSE);
    }

    public Model getModel(String reqName, String body) {
        Model model = new Model();
        try {
            JSONObject rootJson = new JSONObject(body);
            findModel(rootJson, StringHelper.upperCaseFirstLetter(reqName), model);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return model;
    }

    /**
     * 查找用于生成Model的相关信息
     * @param object
     * @return
     * @throws JSONException
     */
    private void findModel(Object object, String modelName, Model model) throws JSONException{

        Set<String> importList = new HashSet<>();
        model.setImportList(importList);

        List<ModelField> fieldList = new ArrayList<>();
        model.setModelFieldList(fieldList);

        model.setModelName(modelName);

        if(object instanceof  JSONObject) {
            JSONObject jsonObject = (JSONObject)object;
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {

                ModelField modelField = new ModelField();
                fieldList.add(modelField);
                String key = iterator.next().toString();
                Object item = jsonObject.get(key);
                if(item instanceof JSONObject) {

                    String subModelName = modelName + StringHelper.upperCaseFirstLetter(key) + "Entity";
                    modelField.setDataType(subModelName);
                    modelField.setRespFieldName(key);
                    modelField.setGenFieldName(key);

                    //需要再创建一个Model
                    Model subModel = new Model();
                    modelField.setSubModel(subModel);

                    //递归查找子Model
                    findModel(item, subModelName, subModel);
                } else if(item instanceof JSONArray) {

                    JSONArray jsonArray = (JSONArray) item;
                    if(jsonArray.length() > 0) {

                        model.getImportList().add(JavaImport.LIST);
                        Object subObject = jsonArray.get(0);
                        if(subObject instanceof JSONObject) {

                            String className = StringHelper.upperCaseFirstLetter(key) + "Entity";
                            String subModelName = modelName + className;
                            modelField.setDataType("List<" + subModelName + ">");
                            modelField.setRespFieldName(key);
                            modelField.setGenFieldName(key);

                            //需要再创建一个Model
                            Model subModel = new Model();
                            modelField.setSubModel(subModel);


                            //递归查找子Model
                            findModel(subObject, subModelName, subModel);
                        } else {

                            if(subObject instanceof Boolean) {
                                modelField.setDataType(DataType.LIST_BOOLEAN);
                            } else if(subObject instanceof Integer) {
                                modelField.setDataType(DataType.LIST_INT);
                            } else if(subObject instanceof Long) {
                                modelField.setDataType(DataType.LIST_LONG);
                            } else if(subObject instanceof Float) {
                                modelField.setDataType(DataType.LIST_FLOAT);
                            } else if(subObject instanceof Double) {
                                modelField.setDataType(DataType.LIST_DOUBLE);
                            } else {
                                modelField.setDataType(DataType.LIST_STRING);
                            }
                            modelField.setRespFieldName(key);
                            modelField.setGenFieldName(key);
                        }
                    }
                } else {
                    if(item instanceof Boolean) {
                        modelField.setDataType(DataType.BOOLEAN);
                    } else if(item instanceof Integer) {
                        modelField.setDataType(DataType.INT);
                    } else if(item instanceof Long) {
                        modelField.setDataType(DataType.LONG);
                    } else if(item instanceof Float) {
                        modelField.setDataType(DataType.FLOAT);
                    } else if(item instanceof Double) {
                        modelField.setDataType(DataType.DOUBLE);
                    } else {
                        modelField.setDataType(DataType.STRING);
                    }
                    modelField.setRespFieldName(key);
                    modelField.setGenFieldName(key);
                }
            }
        }
    }

}
