package tests.api.fasttrack;

import core.BaseApiTest;
import core.TestUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static core.TestUtils.utilRequest;

public class GetFastTrackClassApiTest extends BaseApiTest {
    private static final Logger logger = LogManager.getLogger(GetFastTrackClassApiTest.class);
    private String sessionId;


    private static final String GET_PROGRAM_QUERY = "query userProgramByProgramId($programId: String!, $orderBy: String, $orderColumn: String, $search: String, $page: Float, $limit: Float, $status: [String!], $divisionIds: [String!]) {\n" +
            "  userProgramByProgramId(\n" +
            "    programId: $programId\n" +
            "    orderBy: $orderBy\n" +
            "    orderColumn: $orderColumn\n" +
            "    search: $search\n" +
            "    page: $page\n" +
            "    limit: $limit\n" +
            "    status: $status\n" +
            "    divisionIds: $divisionIds\n" +
            "  ) {\n" +
            "    id\n" +
            "    user {\n" +
            "      id\n" +
            "      name\n" +
            "      __typename\n" +
            "    }\n" +
            "    status\n" +
            "    __typename\n" +
            "  }\n" +
            "}";

    @BeforeClass(alwaysRun = true)
    public void setupAuth() {
        logger.info("Pre-Condition: Mendapatkan Session Cookie untuk Get FTC");
        sessionId = TestUtils.getSid();
        Assert.assertNotNull(sessionId, "Session ID kosong, gagal login!");
    }

    @Test(priority = 1, groups = {"api-test"}, description = "TC-FTC-003 - Get Detail Fast Track Class Valid")
    public void testGetDetailFastTrackClass(ITestContext context) {
        String programId = (String) context.getSuite().getAttribute("sharedProgramId");

        if (programId == null) {
            throw new SkipException("Test di-skip karena sharedProgramId tidak ditemukan.");
        }

        logger.info("Mengeksekusi Query Get Detail untuk Program ID: " + programId);

        Map<String, Object> variables = new HashMap<>();
        variables.put("programId", programId);
        variables.put("orderBy", "DESC");
        variables.put("orderColumn", "");
        variables.put("search", "");
        variables.put("page", 1);
        variables.put("limit", 5);

        // Memanggil variabel statis GET_PROGRAM_QUERY
        Response response = utilRequest(GET_PROGRAM_QUERY, variables, null, null, sessionId);
        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(response.getStatusCode(), 200, "Status HTTP tidak 200");
        Assert.assertNull(jsonPath.get("errors"), "Terdapat error pada query GraphQL");
        Assert.assertNotNull(jsonPath.get("data.userProgramByProgramId"), "Data program tidak ditemukan!");
    }
}