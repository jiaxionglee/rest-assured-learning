package wework;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jiaxiong on 2019-03-24 11:32
 */
class ApiTest {

    @Test
    void templateFromYaml() {
        Api api=new Api();
        api.getResponseFromYaml("/api/list.yaml",null).then().statusCode(200);
    }

    @Test
    void request(){
        RequestSpecification req=given();
        req.queryParam("id",1);
        req.queryParam("id",1);
        req.get("http://www.baidu.com");
    }


}