package ru.netology.conditional;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConditionalApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    TestRestTemplate restTemplate1;
    private static GenericContainer<?> myapp1 = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static GenericContainer<?> myapp2 = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        myapp1.start();
        myapp2.start();
    }

    @Test
    void testDevProfile() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + myapp1.getMappedPort(8080) + "/profile", String.class);
        assertEquals("Current profile is dev", forEntity.getBody());
    }

    @Test
    void testProdProfile() {
        ResponseEntity<String> forEntity1 = restTemplate1.getForEntity("http://localhost:" + myapp2.getMappedPort(8081) + "/profile", String.class);
        assertEquals("Current profile is production", forEntity1.getBody());
    }
}