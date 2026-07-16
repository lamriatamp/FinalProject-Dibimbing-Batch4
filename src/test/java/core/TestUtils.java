package core;

import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import java.util.*;
import static io.restassured.RestAssured.given;
public class TestUtils extends BaseApiTest{

    public static Response utilRequest(String graphqlQuery, Object variables, String username, String password, String sessionId) {
        String contentType = "application/json";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", graphqlQuery);
        if (variables != null) requestBody.put("variables", variables);

        RequestSpecification request = given().contentType(contentType);
// Basic Auth
        if (username != null && !username.isBlank()) {
            request.auth().preemptive().basic(username, password);
        }
// Session Cookie
        if (sessionId != null && !sessionId.isBlank()) {
            request.cookie("sid_b2b", sessionId);
        }

        Response response = request
                .body(requestBody)
                .when()
                .post("/graphql")
                .then()
                .extract()
                .response();

        System.out.println(response.asPrettyString());

        return response;
    }

    public static String getSid() {
        final String mutation = "mutation Login($companyId: String!, $usernameOrEmail: String!, $password: String!) {\n" +
                " login(companyId: $companyId, usernameOrEmail: $usernameOrEmail, password: $password) {\n" +
                " user { id }\n" +
                " errors { field message }\n" +
                " }\n" +
                " }";

        Map<String, Object> variables = new HashMap<>();
        variables.put("usernameOrEmail", config.getProperty("validEmailLogin"));
        variables.put("password", config.getProperty("validPasswordLogin"));
        variables.put("companyId", config.getProperty("companyId"));

        Response response = utilRequest(mutation, variables, null, null, null);

        return response.getCookie("sid_b2b");
    }

}