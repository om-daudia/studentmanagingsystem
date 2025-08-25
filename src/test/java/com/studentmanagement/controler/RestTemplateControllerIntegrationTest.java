package com.studentmanagement.controler;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.dto.RestTemplateResponseDTO;
import com.studentmanagement.resttemplate.RestUniversityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateControllerIntegrationTest {

    @Autowired
    private RestUniversityService restUniversityService;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private String universityUrl = "http://localhost:8081/university";

    private MockRestServiceServer mockServer;

    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate = restTemplateBuilder.build();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        restUniversityService.restTemplate = restTemplate;
    }

    @Test
    public void testGetUniversity() {
        String json = """
                {
                    "id": 1,
                    "name": "gandhinagar University",
                    "city": "gujarat"
                }
                """;

        mockServer.expect(once(), requestTo(universityUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        RestTemplateResponseDTO response = restUniversityService.getUniversity();
        assertNotNull(response);
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("gandhinagar University", response.getName());
        assertEquals("gujarat", response.getCity());
    }

    @Test
    public void testPutUniversity() {
        String json = """
                {
                    "id": 1,
                    "name": "gandhinagar University",
                    "city": "gujarat"
                }
                """;

        mockServer.expect(once(), requestTo(universityUrl)).andExpect(method(HttpMethod.POST)).andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        RestTemplateResponseDTO response = restUniversityService.putUniversity();
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("gandhinagar University", response.getName());
    }

    @Test
    public void testDeleteUniversity() {
        String json = """
                {
                    "id": 1,
                    "name": "gandhinagar University",
                    "city": "gujarat"
                }
                """;

        mockServer.expect(once(), requestTo(universityUrl)).andExpect(method(HttpMethod.DELETE)).andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        RestTemplateResponseDTO response = restUniversityService.deleteUniversity();
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("gandhinagar University", response.getName());
        assertEquals("gujarat", response.getCity());
    }

    @Test
    public void testGetUserFromParent_validId() {
        String json = """
                {
                    "id": 1,
                    "name": "gandhinagar University",
                    "city": "gujarat"
                }
                """;

        mockServer.expect(once(), requestTo(universityUrl + "/" + 1)).andExpect(method(HttpMethod.GET)).andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        RestTemplateResponseDTO response = restUniversityService.getUserFromParent(1);
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("gandhinagar University", response.getName());
        assertEquals("gujarat", response.getCity());
    }

    @Test
    public void testGetUserFromParent_invalidId() {
        int id = 0;
        String errorJson = "university not found with id: 0";

        mockServer.expect(once(), requestTo(universityUrl + "/" + id)).andExpect(method(HttpMethod.GET)).andRespond(withStatus(org.springframework.http.HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorJson));

        try {
            restUniversityService.getUserFromParent(id);
        } catch (ApplicationException e) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
            Assertions.assertEquals("university not found with id: 0", e.getMessage());
        }
    }
}
