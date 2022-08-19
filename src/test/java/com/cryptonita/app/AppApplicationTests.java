package com.cryptonita.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppApplicationTests {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void getCoinsIsNotNull() throws Exception {
       // assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/coins/all", Coins.class)).isNull();
    }

    @Test
    public void getCoinsIsNotEmpty() throws Exception {
        //assertThat(testRestTemplate.getForObject("http://localhost:" + port + "/coins/all", Coins.class)).isNotNull();
    }


}
