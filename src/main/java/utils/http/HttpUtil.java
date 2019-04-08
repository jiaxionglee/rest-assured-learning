package utils.http;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Created by jiaxiong on 2019-03-20 13:45
 */
public class HttpUtil {

    //增加http的配置文件  /src/main/resources/http/httpconf.properties
    private static String propertyFile = "/httpconf.properties";

    static {
        setRestAssuredConfig();
    }

    //Rest-Assured配置
    private static void setRestAssuredConfig() {
        String[] keyList = new String[]{
                "http.connection.timeout",
                "http.socket.timeout",
                "http.connection.manager.timeout"
        };

        HashMap<String, Object> httpClientParams = new HashMap<String, Object>();

        PropertiesUtil.loadProperties(propertyFile);
        for (String key : keyList) {
            httpClientParams.put(key, PropertiesUtil.getValue(key));
        }

        RestAssured.config().httpClient(HttpClientConfig.httpClientConfig().addParams(httpClientParams));
        RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8"));

        String host = PropertiesUtil.getValue("proxy.host");

        //设置代理
        if (!StringUtils.isBlank(host) && PropertiesUtil.getValue("proxy.port")!=null) {
            Integer port = Integer.valueOf(PropertiesUtil.getValue("proxy.port"));
            RestAssured.proxy(host, port);
        }
        RestAssured.replaceFiltersWith(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    public static Response doPostWithNothing(String url) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, null, null, null, null);
    }

    public static Response doPostWithQueryParams(String url, HashMap<String, Object> queryParams) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, null, queryParams, null, null);
    }

    public static Response doPostWithHeaders(String url, HashMap<String, Object> headers) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, headers, null, null, null);
    }

    public static Response doPostWithFormParams(String url, HashMap<String, Object> formParams) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, null, null, formParams, null);
    }

    public static Response doPostWithHeadersAndFormParams(String url, HashMap<String, Object> headers,
                                                          HashMap<String, Object> formParams) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, headers, null, formParams, null);
    }

    public static Response doPostWithHeadersAndQueryParamsAndFormParams(String url, HashMap<String, Object> headers,
                                                                        HashMap<String, Object> queryParams,
                                                                        HashMap<String, Object> formParams) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, headers, queryParams, formParams, null);
    }

    public static Response doPostWithBodyParams(String url, Object bodyParams) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, null, null, null, bodyParams);
    }

    public static Response doPostWithHeadersAndBodyParams(String url, HashMap<String, Object> headers, Object bodyParams) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, headers, null, null, bodyParams);
    }
    public static Response doPostWithHeadersAndQueryParams(String url, HashMap<String, Object> headers,
                                                           HashMap<String, Object> queryParams) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, headers, queryParams, null, null);
    }

    public static Response doPostWithHeadersAndQueryParamsAndBodyParams(String url, HashMap<String, Object> headers,
                                                                        HashMap<String, Object> queryParams,
                                                                        Object bodyParams) {
        return doHttpRequest(url, HttpMethods.POST_METHOD, headers, queryParams, null, bodyParams);
    }

    public static Response doGetWithNothing(String url) {
        return doHttpRequest(url, HttpMethods.GET_METHOD, null, null, null, null);
    }

    public static Response doGetWithHeaders(String url, HashMap<String, Object> headers) {
        return doHttpRequest(url, HttpMethods.GET_METHOD, headers, null, null, null);
    }

    public static Response doGetWithQueryParams(String url, HashMap<String, Object> queryParams) {
        return doHttpRequest(url, HttpMethods.GET_METHOD, null, queryParams, null, null);
    }

    public static Response doGetWithHeadersAndParams(String url, HashMap<String, Object> headers,
                                                     HashMap<String, Object> queryParams) {
        return doHttpRequest(url, HttpMethods.GET_METHOD, headers, queryParams, null, null);
    }

    private static Response doHttpRequest(String url, String method, HashMap<String, Object> headers,
                                          HashMap<String, Object> queryParams,
                                          Map<String, Object> formParams, Object body) {
        Response response = null;
        boolean isSerial = false;

        if (formParams == null && body != null) {
            isSerial = true;
        }

        if (isSerial) {
            if (headers != null && queryParams != null && formParams != null) {
                response = given().log().all()
                        .headers(headers).queryParams(queryParams).formParams(formParams).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers != null && queryParams != null && formParams == null) {
                response = given().log().all()
                        .headers(headers).queryParams(queryParams).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers != null && queryParams == null && formParams != null) {
                response = given().log().all()
                        .headers(headers).formParams(formParams).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers != null && queryParams == null && formParams == null) {
                response = given().log().all()
                        .headers(headers).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers == null && queryParams != null && formParams != null) {
                response = given().log().all()
                        .queryParams(queryParams).formParams(formParams).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers == null && queryParams != null && formParams == null) {
                response = given().log().all()
                        .queryParams(queryParams).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers == null && queryParams == null && formParams != null) {
                response = given().log().all()
                        .formParams(formParams).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers == null && queryParams == null && formParams == null) {
                response = given().log().all()
                        .request(method, url)
                        .then().log().all().extract().response();
            }
        } else {
            if (headers != null && queryParams != null && formParams != null) {
                response = given().log().all()
                        .headers(headers).queryParams(queryParams).body(formParams)
                        .request(method, url)
                        .then().log().all().extract().response();
            } else if (headers != null && queryParams != null && formParams == null) {
                response = given().log().all()
                        .headers(headers).queryParams(queryParams).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers != null && queryParams == null && formParams != null) {
                response = given().log().all()
                        .headers(headers).body(formParams)
                        .request(method, url)
                        .then().log().all().extract().response();
            } else if (headers != null && queryParams == null && formParams == null) {
                response = given().log().all()
                        .headers(headers).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers == null && queryParams != null && formParams != null) {
                response = given().log().all()
                        .queryParams(queryParams).body(formParams)
                        .request(method, url).then().log().all().extract().response();
            } else if (headers == null && queryParams != null && formParams == null) {
                response = given().log().all()
                        .queryParams(queryParams).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers == null && queryParams == null && formParams != null) {
                response = given().log().all()
                        .body(formParams).request(method, url)
                        .then().log().all().extract().response();
            } else if (headers == null && queryParams == null && formParams == null) {
                response = given().log().all()
                        .request(method, url)
                        .then().log().all().extract().response();
            }
        }

        return response;
    }
}
