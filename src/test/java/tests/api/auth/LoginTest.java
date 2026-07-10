package tests.api.auth;

import core.BaseApiTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static core.TestUtils.utilRequest;

public class LoginTest extends BaseApiTest {
    private static final Logger logger = LogManager.getLogger(tests.ui.auth.LoginTest.class);
    private static final String mutation =
            "mutation login($usernameOrEmail: String!, $password: String!, $companyId: String!) {\n" +
                    "   login(    \n" +
                    "    usernameOrEmail: $usernameOrEmail    \n" +
                    "    password: $password    \n" +
                    "    companyId: $companyId) {\n" +
                    "        user { id name username email role } \n" +
                    "        errors { message field }\n" +
                    "    }\n" +
                    "}";

    @Test(priority = 1, groups = {"api-test"}, description = "TC-AUTH-001 - Menguji proses login menggunakan kombinasi Email dan Password yang benar.")
    public void userLoginWithValidCredential() {
        logger.info("Pre-Condition: User sudah memiliki akun");

        Map<String, Object> variables = new HashMap<>();
        variables.put("usernameOrEmail", config.getProperty("validEmailLogin"));
        variables.put("password", config.getProperty("validPasswordLogin"));
        variables.put("companyId", config.getProperty("companyId"));

        // Request
        Response response = utilRequest("login", mutation, variables, null, null, null);
        JsonPath jsonPath = response.jsonPath();

        // Validate base structure
        Assert.assertNotNull(jsonPath.get("data.login"));
        Assert.assertNotNull(jsonPath.get("data.login.user"));
        Assert.assertNull(jsonPath.get("data.login.errors"));
        Assert.assertTrue(jsonPath.get("data.login.user") instanceof Map);

    }
}
