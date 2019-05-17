//package wework.contact;
//
//import com.jayway.jsonpath.JsonPath;
//import io.restassured.response.Response;
//import utils.http.HttpUtil;
//import wework.Wework;
//
//import java.util.HashMap;
///**
// * Created by jiaxiong on 2019-03-17 10:46
// */
//public class Member extends Contact {
//
//    public Response create(HashMap<String, Object> map) {
//        String body = template("/data/member.json", map);
//        return requestSpecification
//                .body(body)
//                .post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
//                .then().log().all().extract().response();
//    }
//
//    public Response getMemberList(String userid) {
//        return requestSpecification
//                .param("userid", userid)
//                .get("https://qyapi.weixin.qq.com/cgi-bin/user/get")
//                .then().log().all().extract().response();
//    }
//
//    public Response update(String userid) {
//        String body = JsonPath.parse(this.getClass()
//                .getResourceAsStream("/data/updateMember.json"))
//                .set("$.userid", userid)
//                .jsonString();
//        return requestSpecification
//                .body(body)
//                .post("https://qyapi.weixin.qq.com/cgi-bin/user/update")
//                .then().log().all().extract().response();
//    }
//
//    public Response delete(String userid) {
//        reset();
//        return requestSpecification
//                .param("userid", userid)
//                .get("https://qyapi.weixin.qq.com/cgi-bin/user/delete")
//                .then().log().all().extract().response();
//    }
//
//    public Response batchDelete(String[] useridlist) {
//        String body=JsonPath.parse(this.getClass()).set("$.useridlist",useridlist).jsonString();
//        return requestSpecification
////                .queryParam("useridlist", useridlist)
//                .body(body)
//                .post("https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete")
//                .then().log().all().extract().response();
//    }
//
//    public Response simpleList(String departId, String fetch_child) {
//        return requestSpecification
//                .param("department_id", departId)
//                .param("fetch_child", fetch_child)
//                .get("https://qyapi.weixin.qq.com/cgi-bin/user/simplelist")
//                .then().log().all().extract().response();
//    }
//
//    public Response list(String departId, String fetch_child) {
//        return requestSpecification
//                .param("department_id", departId)
//                .param("fetch_child", fetch_child)
//                .get("https://qyapi.weixin.qq.com/cgi-bin/user/list")
//                .then().log().all().extract().response();
//    }
//
//    public Response list_self(String departId, String fetch_child) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("department_id", departId);
//        map.put("fetch_child", fetch_child);
//        map.put("access_token", Wework.getToken());
//        return HttpUtil.doGetWithQueryParams("https://qyapi.weixin.qq.com/cgi-bin/user/list",map);
//    }
//
//    public Response update_self(String userid) {
//        HashMap<String,Object> param = new HashMap<>();
//        param.put("userid", userid);
//        HashMap<String,Object> headers = new HashMap<>();
//        headers.put("access_token",Wework.getToken());
//        return HttpUtil.doPostWithHeadersAndQueryParams("https://qyapi.weixin.qq" +
//                ".com/cgi-bin/user/update",headers,param);
//    }
//}
