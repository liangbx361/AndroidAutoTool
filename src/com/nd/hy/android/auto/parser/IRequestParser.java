package com.nd.hy.android.auto.parser;

import java.util.Map;

/**
 * Author liangbx
 * Date 2015/9/1
 */
public interface IRequestParser {

    /**
     * 获取请求的路径
     * @param url
     * @param baseUrl
     * @return
     */
    String getReqUrl(String url, String baseUrl);

    /**
     * 获取请求的参数列表
     * @param url
     * @return
     */
    Map<String, String> getReqParams(String url);

}
