package wework;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * Created by jiaxiong on 2019-03-17 14:46
 */
public class Restful {

    HashMap<String, Object> query = new HashMap<String, Object>();
    public RequestSpecification requestSpecification = given();

    public Response send() {
        requestSpecification = given().log().all();
        query.entrySet().forEach(entry -> {
            given().queryParam(entry.getKey(), entry.getValue());
        });

        return requestSpecification.when().request("get", "baidu.com");
    }

    public void reset() {
        requestSpecification = given().log().all();
    }

    public static String template(String path, HashMap<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(Restful.class.getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    public Response templateFromHar(String pattern,String path,HashMap<String,Object> map){
        //从har读取请求，进行更新
        DocumentContext documentContext = JsonPath.parse(Restful.class.getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });

        String method=documentContext.read("method");
        String url=documentContext.read("url");

        return requestSpecification.when().request(method,url);
    }
}
