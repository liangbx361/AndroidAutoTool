package com.nd.hy.android.auto.parser;

import com.nd.hy.android.auto.define.TmplModelFields;
import com.nd.hy.android.auto.define.JavaImport;
import com.nd.hy.android.auto.define.TmplComFields;
import com.nd.hy.android.auto.model.Model;
import com.nd.hy.android.auto.model.ModelField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static com.nd.hy.android.auto.define.TmplModelFields.*;

/**
 * Author liangbx
 * Date 2015/8/25
 */
public class JsonRespParser implements ResponseParser {

    public static final String TEST_RESPONSE = "{\"a\":[1,2,3,4,5,6],\"b\":[{\"b1\":\"1\"},{\"b2\":\"2\"}],\"c\":true,\"d\":10,\"e\":\"e\",\"f\":1.3,\"g\":{\"g1\":[\"g1\",\"g2\"],\"g2\":[{\"g21\":\"g21\",\"g22\":\"g22\"}]}}";

    public static void main(String[] args) {
//        ResponseParser respParser = new JsonRespParser();
//        respParser.getModelParams(TEST_RESPONSE);
//        respParser.getModel(TEST_RESPONSE);
    }

    public Model getModel(String body) {
        Model model = new Model();
        try {
            JSONObject rootJson = new JSONObject(body);
            findModel(rootJson, model);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return model;
    }

//    public Map<String, Object> getModelParams(String body) {
//
//        Map<String, Object> model = new HashMap<>();
//        try {
//            JSONObject rootJson = new JSONObject(body);
//            findModelToMap(rootJson, model);
//            System.out.println(model);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return model;
//    }

    /**
     * 查找用于生成Model的相关信息
     * @param object
     * @return
     * @throws JSONException
     */
    private void findModel(Object object, Model model) throws JSONException{

        Set<String> importList = new HashSet<>();
        model.setImportList(importList);

        List<ModelField> fieldList = new ArrayList<>();
        model.setModelFieldList(fieldList);

        if(object instanceof  JSONObject) {
            JSONObject jsonObject = (JSONObject)object;
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {

                ModelField modelField = new ModelField();
                fieldList.add(modelField);
                String key = iterator.next().toString();
                Object item = jsonObject.get(key);
                if(item instanceof JSONObject) {

                    modelField.setDataType(upperCaseFirstLetter(key) + "Entity");
                    modelField.setRespFieldName(key);
                    modelField.setGenFieldName(key);

                    //需要再创建一个Model
                    Model subModel = new Model();
                    modelField.setSubModel(subModel);
                    subModel.setModelName(modelField.getDataType());

                    //递归查找子Model
                    findModel(item, subModel);
                } else if(item instanceof JSONArray) {

                    JSONArray jsonArray = (JSONArray) item;
                    if(jsonArray.length() > 0) {

                        model.getImportList().add(JavaImport.LIST);
                        Object subObject = jsonArray.get(0);
                        if(subObject instanceof JSONObject) {


                            String className = upperCaseFirstLetter(key) + "Entity";
                            modelField.setDataType("List<" + className + ">");
                            modelField.setRespFieldName(key);
                            modelField.setGenFieldName(key);

                            //需要再创建一个Model
                            Model subModel = new Model();
                            modelField.setSubModel(subModel);
                            subModel.setModelName(className);

                            //递归查找子Model
                            findModel(item, subModel);
                        } else {

                            if(subObject instanceof Boolean) {
                                modelField.setDataType("List<Boolean>");
                            } else if(subObject instanceof Integer) {
                                modelField.setDataType("List<Integer>");
                            } else if(subObject instanceof Long) {
                                modelField.setDataType("List<Integer>");
                            } else if(subObject instanceof Float) {
                                modelField.setDataType("List<Integer>");
                            } else if(subObject instanceof Double) {
                                modelField.setDataType("List<Integer>");
                            } else {
                                modelField.setDataType("List<String>");
                            }
                            modelField.setRespFieldName(key);
                            modelField.setGenFieldName(key);
                        }
                    }
                } else {
                    if(item instanceof Boolean) {
                        modelField.setDataType("boolean");
                    } else if(item instanceof Integer) {
                        modelField.setDataType("int");
                    } else if(item instanceof Long) {
                        modelField.setDataType("long");
                    } else if(item instanceof Float) {
                        modelField.setDataType("float");
                    } else if(item instanceof Double) {
                        modelField.setDataType("double");
                    } else {
                        modelField.setDataType("String");
                    }
                    modelField.setRespFieldName(key);
                    modelField.setGenFieldName(key);
                }
            }
        }
    }

    /**
     * 查找用于生成Model的相关信息
     * @param object
     * @return
     * @throws JSONException
     */
    private void findModelToMap(Object object, Map<String, Object> model) throws JSONException{

        Set<String> importList = new HashSet<>();
        model.put(TmplComFields.IMPORT_LIST, importList);

        List<Map<String, Object>> fieldsList = new ArrayList<>();
        model.put(TmplModelFields.FIELDS_LIST, fieldsList);

        if(object instanceof  JSONObject) {
            JSONObject jsonObject = (JSONObject)object;
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {

                Map<String, Object> map = new HashMap<>();
                String key = iterator.next().toString();
                Object item = jsonObject.get(key);
                if(item instanceof JSONObject) {

                    Map<String, Object> subModel = new HashMap<>();
                    map.put(TmplModelFields.SUB_CLASS, subModel);
                    map.put(CLASS_NAME, upperCaseFirstLetter(key)+"Entity");
                    map.put(FIELDS_TYPE, map.get(CLASS_NAME));
                    map.put(TmplModelFields.FIELDS_NAME, key);
                    map.put(FN_NAME, upperCaseFirstLetter(key));
                    fieldsList.add(map);

                    //递归查找子Model
                    findModelToMap(item, subModel);
                } else if(item instanceof JSONArray) {

                    JSONArray jsonArray = (JSONArray) item;
                    if(jsonArray.length() > 0) {

                        Object subObject = jsonArray.get(0);
                        if(subObject instanceof JSONObject) {

                            Map<String, Object> subModel = new HashMap<>();
                            map.put(TmplModelFields.SUB_CLASS, subModel);
                            map.put(CLASS_NAME, upperCaseFirstLetter(key)+"Entity");
                            map.put(FIELDS_TYPE, "List<" + map.get(CLASS_NAME) + ">");
                            map.put(TmplModelFields.FIELDS_NAME, key);
                            map.put(FN_NAME, upperCaseFirstLetter(key));
                            fieldsList.add(map);
                            importList.add(JavaImport.LIST);

                            //递归查找子Model
                            findModelToMap(item, subModel);
                        } else {

                            if(subObject instanceof Boolean) {
                                map.put(FIELDS_TYPE, "List<Boolean>");
                            } else if(subObject instanceof Integer) {
                                map.put(FIELDS_TYPE, "List<Integer>");
                            } else if(subObject instanceof Long) {
                                map.put(FIELDS_TYPE, "List<Long>");
                            } else if(subObject instanceof Float) {
                                map.put(FIELDS_TYPE, "List<Float>");
                            } else if(subObject instanceof Double) {
                                map.put(FIELDS_TYPE, "List<Double>");
                            } else {
                                map.put(FIELDS_TYPE, "List<String>");
                            }
                            map.put(TmplModelFields.FIELDS_NAME, key);
                            map.put(FN_NAME, upperCaseFirstLetter(key));
                            fieldsList.add(map);
                            importList.add(JavaImport.LIST);
                        }
                    }
                } else {
                    if(item instanceof Boolean) {
                        map.put(FIELDS_TYPE, "boolean");
                    } else if(item instanceof Integer) {
                        map.put(FIELDS_TYPE, "int");
                    } else if(item instanceof Long) {
                        map.put(FIELDS_TYPE, "long");
                    } else if(item instanceof Float) {
                        map.put(FIELDS_TYPE, "float");
                    } else if(item instanceof Double) {
                        map.put(FIELDS_TYPE, "double");
                    } else {
                        map.put(FIELDS_TYPE, "String");
                    }
                    map.put(TmplModelFields.FIELDS_NAME, key);
                    map.put(FN_NAME, upperCaseFirstLetter(key));
                    fieldsList.add(map);
                }
            }
        }
    }

    /**
     * 对首字母转换成大写
     * @param name
     * @return
     */
    private String upperCaseFirstLetter(String name){
        if(name != null && name.length() > 0) {
            char[] chars = name.toCharArray();
            if(chars[0]>= 97 && chars[0]<=122) {
                chars[0] -= 32;
            }
            return String.valueOf(chars);
        }
        return name;
    }

}
