package com.studentmanagement.awstest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.studentmanagement.config.AwsClientFactory;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WireMockAWSTest {
    static private WireMockServer wireMockServer;
    @BeforeAll
    static void setup(){
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", 8089);

        // put method stub
        stubFor(put(urlEqualTo("/my-bucket/mynote.txt"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("successfully")));
        stubFor(put(urlEqualTo("/my-bucket/mynote2.txt"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("page not found")));

        // get method stub
        stubFor(get(urlEqualTo("/my-bucket/mynote.txt"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("successfully")));

        stubFor(get(urlEqualTo("/my-bucket/mynote2.txt"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("page not found")));

    }
    @AfterAll
    static void stopServer() {
        wireMockServer.stop();
    }

    @Test
    void addObjectSuccessTest() throws IOException {
        URL url = new URL("http://localhost:8089/my-bucket/mynote.txt");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.getOutputStream().write("my name is om".getBytes());
        conn.connect();
        Assertions.assertEquals(200, conn.getResponseCode());
        Assertions.assertEquals("successfully", conn.getResponseMessage());
    }
    @Test
    void addObjectFailTest() throws IOException {
        URL url = new URL("http://localhost:8089/my-bucket/mynote2.txt");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.getOutputStream().write("my name is om".getBytes());
        conn.connect();
        Assertions.assertEquals(404, conn.getResponseCode());
        Assertions.assertEquals("successfully", conn.getResponseMessage());
    }

    @Test
    void getObjectSuccessTest() throws IOException {
        URL url = new URL("http://localhost:8089/my-bucket/mynote.txt");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Assertions.assertEquals(200, conn.getResponseCode());
        Assertions.assertEquals("successfully", conn.getResponseMessage());
    }
    @Test
    void getObjectFailTest() throws IOException {
        URL url = new URL("http://localhost:8089/my-bucket/mynote2.txt");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        Assertions.assertEquals(404, conn.getResponseCode());
        Assertions.assertEquals("page not found", conn.getResponseMessage());
    }

    @Test
    void addDataSuccessTest(){
        stubFor(put(urlEqualTo("/my-bucket/mynote.txt"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("successfully")));
        S3Client s3 = AwsClientFactory.mockS3Client("http://localhost:8089");
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("my-bucket")
                .key("mynote.txt")
                .build();
        PutObjectResponse response = s3.putObject(request, RequestBody.fromString("my name is om"));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.sdkHttpResponse().statusCode());
    }
    @Test
    void addDataFailTest(){
        stubFor(put(urlEqualTo("/my-bucket/mynote2.txt"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withBody("page not found")));
        S3Client s3 = AwsClientFactory.mockS3Client("http://localhost:8089");
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("my-bucket")
                .key("mynote2.txt")
                .build();
        try {
            s3.putObject(request, RequestBody.fromString("my name is om"));
        } catch (S3Exception e) {
            Assertions.assertEquals(404, e.statusCode());
        }
    }
}
