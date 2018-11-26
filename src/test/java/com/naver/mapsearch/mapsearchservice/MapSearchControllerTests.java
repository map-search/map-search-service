package com.naver.mapsearch.mapsearchservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MapSearchControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void HelloWorldResonseTest() throws Exception{

        webTestClient.get().uri("/helloworld/ddolsoon")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("hello, world!! your name is ddolsoon");
    }
}
