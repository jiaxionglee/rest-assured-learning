import org.junit.jupiter.api.Test;
import wework.Wework;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * Created by jiaxiong on 2019-03-17 10:16
 */
public class testGetToken {

    @Test
    public void testToken() {
        Wework wework = new Wework();
        String token = wework.getToken();

        assertThat(token, not(equalTo(null)));
    }
}
