package com.nd.hy.android.auto.util;

/**
 * Author liangbx
 * Date 2015/8/28
 */
public class StringHelper {

    /**
     * 对首字母转换成大写
     * @param name
     * @return
     */
    public static String upperCaseFirstLetter(String name){
        if(name != null && name.length() > 0) {
            char[] chars = name.toCharArray();
            if(chars[0]>= 97 && chars[0]<=122) {
                chars[0] -= 32;
            }
            return String.valueOf(chars);
        }
        return name;
    }

    public static String getClassName(String name) {
        if(name.contains("List<")) {
            name = name.replace("List<", "");
            name = name.replace(">", "");
        }
        return name;
    }
}
