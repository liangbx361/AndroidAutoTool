package com.nd.hy.android.auto.parser;

import com.nd.hy.android.auto.model.Model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author liangbx
 * Date 2015/8/25
 * DESC 对Http通过的响应数据进行解析，分析出数据结构，提取出数据的Model
 */
public interface ResponseParser {

    /**
     * 将body转换成Model
     * @param response
     * @return
     */
    Model getModel(String body);
}
