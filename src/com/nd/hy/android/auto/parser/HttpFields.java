package com.nd.hy.android.auto.parser;

/**
 * Author liangbx
 * Date 2015/9/1
 */
public interface HttpFields {

    String HTTP_LIST = "httpList";

    String REQUEST = "request";

    String REQUEST_NAME = "RequestName";

    /**
     * 请求路径
     */
    String REQUEST_PATH = "RequestPath";

    /**
     * 请求方法
     */
    String REQUEST_METHOD = "RequestMethod";

    /**
     * 请求参数
     */
    String REQUEST_PARAMS = "RequestParams";

    /**
     *
     */
    String REQUEST_FN_NAME = "RequestFnName";

    String REQUEST_DESCRIPTION = "description";

    String RESPONSE = "response";

    String RESPONSE_BODY = "body";


}
