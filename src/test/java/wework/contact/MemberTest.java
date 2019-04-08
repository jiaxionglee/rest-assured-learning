package wework.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * Created by jiaxiong on 2019-03-17 16:01
 */
class MemberTest {

    private static Member member;
    private String fetch_child="0";

    @BeforeEach
    void setUp() {
        member = new Member();
    }

    @ParameterizedTest
    @ValueSource(strings={"test_","java_"})
    void create(String name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", name + member.random);
        map.put("name", "name_" + member.random);
        map.put("department", Arrays.asList(1, 2));
        map.put("mobile", "170" + member.random.substring(5+0, 5+8));
        map.put("email", member.random.substring(5+0,5+8) + "@qq.com");

        member.create(map).then().statusCode(200).body("errcode", equalTo(0));
    }

    @Test
    void getMemberList() {
        member.getMemberList("csv_11552827968924").then().statusCode(200).body("errcode",equalTo(0));
    }

    @Test
    void update() {

        member.update("csv_21552827968924").then().statusCode(200)
                .body("errcode",equalTo(0))
                .body("errmsg",equalTo("updated"));
    }

    @Test
    void delete() {
        member.delete("csv_11552827968924").then().statusCode(200)
                .body("errcode",equalTo(0))
                .body("errmsg",equalTo("deleted"));

        member.simpleList("2",fetch_child).then().statusCode(200)
                .body("userlist.userid[0]",not(equalTo("csv_11552827968924")));
    }

    @Test
    void simpleList() {
        member.simpleList("2",fetch_child).then().statusCode(200);
    }

    @Test
    void batchDelete() {

        member.batchDelete(new String[]{"csv_21552827968924","csv_31552827968924"}).then().statusCode(200)
                .body("errcode",equalTo(0))
                .body("errmsg",equalTo("deleted"));

        member.simpleList("2",fetch_child).then().statusCode(200)
                .body("userlist.userid[0]",not(equalTo("csv_21552827968924")))
                .body("userlist.userid[0]",not(equalTo("csv_31552827968924")));
    }

    @Test
    void list() {
        member.list("1",fetch_child).then().statusCode(200);
    }

    @Test
    void list_self() {
        member.list_self("1",fetch_child).then().statusCode(200);
    }

    @Test
    void update_self() {
        member.update_self("csv_31552827968924").then().statusCode(200)
                .body("errcode",equalTo(0))
                .body("errmsg",equalTo("updated"));
    }
}