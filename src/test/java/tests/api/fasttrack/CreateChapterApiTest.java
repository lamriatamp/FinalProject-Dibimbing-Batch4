package tests.api.fasttrack;

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

public class CreateChapterApiTest extends BaseApiTest {
    private static final Logger logger = LogManager.getLogger(CreateChapterApiTest.class);

    // Mutation diletakkan di level class seperti LogoutTest
    private static final String mutation =
            "mutation createChapter($input: ChapterInput!) {\n" +
                    "  createChapter(input: $input) {\n" +
                    "    id\n" +
                    "    __typename\n" +
                    "  }\n" +
                    "}";

    @Test(priority = 1, groups = {"api-test"}, description = "TC-FTC-026 - Create Chapter dengan ID Program Statis")
    public void mutationCreateChapter() {
        logger.info("Pre-Condition: Mengambil SID untuk request API");
        String sid = TestUtils.getSid();

        // 1. Setup Input Data (sesuai payload Postman Anda)
        Map<String, Object> inputData = new HashMap<>();
        inputData.put("title", "newad");
        inputData.put("description", "");
        inputData.put("order", 0);
        inputData.put("programId", "f5024efc-fdde-4134-8ee8-ab9ecf09d7ad"); // ID dimasukkan langsung

        // 2. Membungkus ke dalam object "input"
        Map<String, Object> variables = new HashMap<>();
        variables.put("input", inputData);

        // 3. Request (menggunakan utilRequest persis seperti LogoutTest)
        Response response = utilRequest(mutation, variables, null, null, sid);
        JsonPath jsonPath = response.jsonPath();

        // 4. Validate base structure
        Assert.assertEquals(response.getStatusCode(), 200, "Status code HTTP harus 200");
        Assert.assertNull(jsonPath.get("errors"), "Ditemukan error GraphQL pada response");
        Assert.assertNotNull(jsonPath.get("data.createChapter"), "Objek createChapter tidak boleh null");
        Assert.assertNotNull(jsonPath.getString("data.createChapter.id"), "ID Chapter gagal terbuat");

        logger.info("Berhasil membuat Chapter dengan ID: " + jsonPath.getString("data.createChapter.id"));
    }
}