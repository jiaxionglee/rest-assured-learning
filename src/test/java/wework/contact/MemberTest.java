package wework.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jiaxiong on 2019-03-17 16:01
 */
class MemberTest {

    static Member member;

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
        map.put("userid", "userid_" + member.random);

        member.create(map).then().statusCode(200).body("errcode", equalTo(0));
    }
}