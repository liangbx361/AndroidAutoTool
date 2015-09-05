package com.nd.hy.android.auto.model;

import java.util.List;

/**
 * Author liangbx
 * Date 2015/9/1
 */
public class Postman {

    public List<Request> requests;

    public class Request {
        public String url;
        public String method;
        public String name;
        public String description;
        public List<Response> responses;
    }

    public class Response {
        public String text;
        public ResponseCode responseCode;
    }

    public class ResponseCode {
        public String code;
        public String name;
        public String detail;
    }
}
