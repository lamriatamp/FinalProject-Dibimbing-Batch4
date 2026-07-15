package tests.api.auth;

import core.BaseApiTest;
import core.TestUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static core.TestUtils.utilRequest;

public class LogoutTest extends BaseApiTest {
    private static final Logger logger = LogManager.getLogger(tests.ui.auth.LoginTest.class);
    private static final String mutation =
            "mutation logout {logout}";

    @Test(priority = 1, groups = {"api-test"}, description = "TC-AUTH-008 - Verifikasi Logout berhasil dilakukan")
    public void mutationLogout() {
        logger.info("Pre-Condition: User sudah melakukan login");
        String sid = TestUtils.getSid();

        // Request
        Response response = utilRequest(mutation, null, config.getProperty("validEmailLogin"), config.getProperty("validPasswordLogin"), sid);
        JsonPath jsonPath = response.jsonPath();

        // Validate base structure
        Assert.assertNotNull(jsonPath.get("data.logout"));
        Assert.assertTrue(jsonPath.get("data.logout") instanceof Boolean);
        Assert.assertTrue(jsonPath.getBoolean("data.logout"));

    }
}
