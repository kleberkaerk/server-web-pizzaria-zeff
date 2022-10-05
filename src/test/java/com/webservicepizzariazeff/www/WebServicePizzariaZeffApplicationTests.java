package com.webservicepizzariazeff.www;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SuppressWarnings("java:S2699")
class WebServicePizzariaZeffApplicationTests {

//    docker-compose must be running for this test to pass
    @Test
    void contextLoads() {

        Assertions.assertThatCode(() -> WebServicePizzariaZeffApplication.main(new String[]{}))
                .doesNotThrowAnyException();
    }

}
