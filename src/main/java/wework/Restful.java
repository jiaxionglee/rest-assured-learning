package wework;

import java.util.HashMap;

/**
 * Created by jiaxiong on 2019-03-24 11:24
 */
public class Restful {

    public String url;
    public String method;
    public HashMap<String,String> headers;
    public HashMap<String,String> query=new HashMap<String, String>();
    public String body;
}
