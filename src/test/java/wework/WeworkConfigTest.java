package wework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jiaxiong on 2019-03-24 10:40
 */
class WeworkConfigTest {

    @Test
    void load() {
        WeworkConfig.load("");
    }
    @Test
    void getInstance(){
        WeworkConfig.getInstance();
    }
}