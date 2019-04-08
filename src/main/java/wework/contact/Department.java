package wework.contact;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;


/**
 * Created by jiaxiong on 2019-03-17 10:45
 */
public class Department extends Contact {

    public Response list(String id) {
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        return getResponseFromYaml("/api/list.yaml",map);
    }

    public Response create(String name, String parentid) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("parentid",parentid);
        map.put("_file","/data/create.json");
        return getResponseFromYaml("/api/create.yaml",map);
    }

    public Response create(HashMap<String, Object> map) {
        map.put("_file","/data/create.json");
        return getResponseFromYaml("/api/create.yaml",map);
    }

    public Response update(String name, String id) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("id",id);
        map.put("_file","/data/create.json");
        return getResponseFromYaml("/api/update.yaml",map);
    }

    public Response update(HashMap<String,Object> map){
        return getResponseFromHar("url","/data/**.json",map);
    }

    public Response delete(String id) {
        HashMap<String,Object> map=new HashMap<String, Object>();
        map.put("id",id);
        return getResponseFromYaml("/api/delete.yaml",map);
    }

    public Response deleteAll() {
        List<Integer> idList = list("").then().log().all().extract().path("department.id");
        idList.forEach(id->delete(id.toString()));
        return null;
    }

}
