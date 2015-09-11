package com.nd.hy.android.auto.util;

import com.nd.hy.android.auto.define.DataType;

import javax.xml.crypto.Data;

/**
 * Author liangbx
 * Date 2015/9/11
 */
public class DataTypeUtil {

    public static String valutToDataType(String value) {

        String dataType = DataType.STRING;
        try {
            Integer.valueOf(value);
            dataType = DataType.INT;
        } catch (Exception e) {
        }

        if("true".equals(value) || "false".equals(value)) {
            dataType = "boolean";
        }

        return dataType;
    }

    public static String checkTemplateType(String type) {

        if(!DataType.INT.equals(type) && !DataType.BOOLEAN.equals(type) &&
                !DataType.DOUBLE.equals(type) && !DataType.FLOAT.equals(type) &&
                !DataType.STRING.equals(type) && !DataType.LIST_BOOLEAN.equals(type) &&
                !DataType.LIST_DOUBLE.equals(type) && !DataType.LIST_FLOAT.equals(type) &&
                !DataType.LIST_BOOLEAN.equals(type) && !DataType.LIST_INT.equals(type) &&
                !DataType.LIST_LONG.equals(type) && !DataType.LIST_STRING.equals(type)) {
            type = DataType.TEMPLATE;
        }

        return type;
    }
}
