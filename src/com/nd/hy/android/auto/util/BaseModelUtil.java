package com.nd.hy.android.auto.util;

import com.nd.hy.android.auto.MainApp;
import com.nd.hy.android.auto.define.DataType;
import com.nd.hy.android.auto.model.HttpInfo;
import com.nd.hy.android.auto.model.Model;
import com.nd.hy.android.auto.model.ModelField;

import java.util.List;

/**
 * Author liangbx
 * Date 2015/9/11
 * 分析所有Model数据，提取出Base部分
 */
public class BaseModelUtil {

    /**
     * 查换并提取出BaseModel
     * FIXME 目前只支持整层做为BasedModel，后续可以优化为支持同级部分字段提取BaseModel
     */
    public static Model findBaseModel(List<HttpInfo> httpInfoList) {

        boolean isFind = true;
        Model baseModel = null;
        Model firstModel = httpInfoList.get(0).getResponse().getModel();

        for(int i=1; i<httpInfoList.size(); i++) {
            Model itemModel = httpInfoList.get(i).getResponse().getModel();
            if(firstModel.getModelFieldList().size() != itemModel.getModelFieldList().size()) {
                isFind = false;
                break;
            }

            for(int j=0; j<firstModel.getModelFieldList().size(); j++) {
                if(!firstModel.getModelFieldList().get(j).equals(itemModel.getModelFieldList().get(j))) {
                    isFind = false;
                    break;
                }
            }
        }

        if(isFind) {
            baseModel = firstModel.copy();
            for(ModelField modelField : baseModel.getModelFieldList()) {
                modelField.setSubModel(null);
            }
            for(ModelField modelField :baseModel.getModelFieldList()) {
                modelField.setDataType(DataTypeUtil.checkTemplateType(modelField.getDataType()));
            }
            baseModel.setModelName("BaseModel");
            MainApp.project.setBaseModel(baseModel);
        }

        return baseModel;
    }

    /**
     * 更新Model将BaseModel舍弃
     * @param baseModel
     * @param httpInfoList
     */
    public void updateModels(Model baseModel, List<HttpInfo> httpInfoList) {
        for(HttpInfo httpInfo : httpInfoList) {
            Model model = httpInfo.getResponse().getModel();
            Model suModel = updateModel(baseModel, model);
            if(null != suModel) {
                httpInfo.getResponse().setModel(suModel);
            }
        }
    }

    //FIXME 目前只考虑整层提取作为BaseModel，后续可考虑同一级部分进行Base抽取
    private Model updateModel(Model baseModel, Model model) {

        if(baseModel.getModelFieldList().size() == model.getModelFieldList().size()) {
            for(ModelField modelField : model.getModelFieldList()) {
                if(null != modelField.getSubModel()) {
                    for(ModelField baseField : baseModel.getModelFieldList()) {
                        if(baseField.getDataType().equals(DataType.TEMPLATE)) {
                            Model subModel = modelField.getSubModel();
                            subModel.setModelName(model.getModelName());
                            return subModel;
                        }
                    }
                }
            }
        }

        return null;
    }
}
