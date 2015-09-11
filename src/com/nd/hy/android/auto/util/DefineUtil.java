package com.nd.hy.android.auto.util;

import com.nd.hy.android.auto.define.DataType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Author liangbx
 * Date 2015/9/11
 */
public class DefineUtil {

    public static List<String> getDefineList(Class cls) {
        List<String> defList = new ArrayList<>();
        Field[] fields = cls.getFields();
        for(int i=0; i<fields.length; i++) {
            try {
                defList.add(fields[i].get(fields[i].getName()).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return defList;
    }
}
