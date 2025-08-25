package com.studentmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.studentmanagement.dto.RestTemplateResponseDTO;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class RestTemplateControllerTest {
    static private WireMockServer wireMockServer;

    ObjectMapper objectMapper = new ObjectMapper();
    private String apiUrl = "http://localhost:8089/university";
    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8089));
        wireMockServer.start();
        configureFor("localhost", 8089);
    }
    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
    }
    @Test
    void getUniversitySuccessTest()  {
        try {
            RestTemplateResponseDTO response = new RestTemplateResponseDTO(1,"gandhinagar university", "gujarat");
            stubFor(get(urlEqualTo("/university"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withBody(objectMapper.writeValueAsString(response))));
            URL url = new URL("http://localhost:8089/university");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            Assertions.assertEquals(200, con.getResponseCode());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
