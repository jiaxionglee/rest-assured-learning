package wework.contact;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;


/**
 * Created by jiaxiong on 2019-03-17 10:45
 */
public class Department extends Contact {

    public Response list(String id) {
        Response response = requestSpecification
                .param("id", id)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().log().all()
                .statusCode(200).extract().response();
        reset();
        return response;
    }

    public Response create(String name, String parentid) {
        reset();
        String body = JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/create.json"))
                .set("$.name", name)
                .set("parentid", parentid).jsonString();

        return requestSpecification
                .body(body)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .statusCode(200).extract().response();
    }

    public Response create(HashMap<String, Object> map) {
        reset();
        DocumentContext documentContext = JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/create.json"));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });

        return requestSpecification
                .body(documentContext.jsonString())
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all()
                .statusCode(200).extract().response();
    }

    public Response update(String name, String id) {
        reset();
        String body = JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/update.json"))
                .set("$.name", name)
                .set("id", id)
                .jsonString();

        return requestSpecification
                .body(body)
                .when()
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().log().all()
                .statusCode(200).extract().response();
    }

    public Response update(HashMap<String,Object> map){
        return templateFromHar("url","/data/**.json",map);
    }

    public Response delete(String id) {
        reset();
        return requestSpecification
                .param("id", id)
                .when()
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().log().all()
                .statusCode(200).extract().response();
    }

    public Response deleteAll() {
        reset();
        List<Integer> idList = list("").then().log().all().extract().path("department.id");
        idList.forEach(id->delete(id.toString()));
        return null;
    }

}
