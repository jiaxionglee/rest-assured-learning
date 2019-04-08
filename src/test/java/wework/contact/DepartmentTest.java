package wework.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by jiaxiong on 2019-03-17 10:58
 */
class DepartmentTest {

    Department department;

    @BeforeEach
    void setUp() {
        if (department == null) {
            department = new Department();
            department.deleteAll();
        }
    }

    @Test
    void list() {
        department.list("").then().statusCode(200);
    }

    @Test
    void create() {
        department.create("20190317", "1").then().statusCode(200);
    }

    @Test
    void createByMap() {
        HashMap<String,Object> map=new HashMap<String, Object>(){{
            put("name",String.format("20190317_%s",department.random));
            put("parentid","1");
        }};
        department.create(map).then().statusCode(200);
    }

    @Test
    void update() {
        Integer id = department.create("20190317", "2").then().extract().path("id");
        department.update("test", String.valueOf(id))
                .then()
                .body("errcode", equalTo(0))
                .body("errmsg", equalTo("updated"));
    }

    @Test
    void delete() {
        Integer id = department.create("name", "1").then().extract().path("id");
        department.delete(id.toString())
                .then()
                .body("errcode", equalTo(0))
                .body("errmsg", equalTo("deleted"));
    }

    @Test
    void deleteAll() {
        department.deleteAll();
    }
}