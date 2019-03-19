package wework.contact;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

/**
 * Created by jiaxiong on 2019-03-17 10:46
 */
public class Member extends Contact{

    public Response create(HashMap<String,Object> map){
        String body=template("/data/member.json",map);
        reset();
        return requestSpecification
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();
    }

    public Response getMemberList(String userid){
        reset();

        return requestSpecification
                .param("userid",userid)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
                .then().log().all().extract().response();
    }

    public Response update(String userid){
        reset();
        String body = JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/updateMember.json"))
                .set("$.userid", userid)
                .jsonString();
        return requestSpecification
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/update")
                .then().log().all().extract().response();
    }

    public Response delete(String userid){
        reset();
        return requestSpecification
                .param("userid",userid)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/delete")
                .then().log().all().extract().response();
    }

    public Response batchDelete(List<String> list){
//        String body=template()
        reset();
        return requestSpecification
                .queryParam("useridlist",list)
                .post("https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete")
                .then().log().all().extract().response();
    }

    public Response simpleList(String departId,String fetch_child){
        reset();
        return requestSpecification
                .param("department_id",departId)
                .param("fetch_child",fetch_child)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist")
                .then().log().all().extract().response();
    }

    public Response list(String departId,String fetch_child){
        reset();
        return requestSpecification
                .param("department_id",departId)
                .param("fetch_child",fetch_child)
                .get("https://qyapi.weixin.qq.com/cgi-bin/user/list")
                .then().log().all().extract().response();
    }
}
