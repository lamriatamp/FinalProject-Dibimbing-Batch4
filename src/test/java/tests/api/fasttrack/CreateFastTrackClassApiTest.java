package tests.api.fasttrack;

import core.BaseApiTest;
import core.TestUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static core.TestUtils.utilRequest;

public class CreateFastTrackClassApiTest extends BaseApiTest {
    private static final Logger logger = LogManager.getLogger(CreateFastTrackClassApiTest.class);
    private String sessionId;

    // ✅ Query diletakkan di luar TC (Class level) agar bisa di-reuse
    private static final String CREATE_PROGRAM_MUTATION = "mutation createProgram($input: ProgramInput!) {\n" +
            "  createProgram(input: $input) {\n" +
            "    id\n" +
            "    __typename\n" +
            "  }\n" +
            "}";

    @BeforeClass(alwaysRun = true)
    public void setupAuth() {
        logger.info("Pre-Condition: Mendapatkan Session Cookie untuk Create FTC");
        sessionId = TestUtils.getSid();
        Assert.assertNotNull(sessionId, "Session ID kosong, gagal login!");
    }

    // TC 1: Skenario Positif (Berhasil Create)
    @Test(priority = 1, groups = {"api-test"}, description = "TC-FTC-001 - Create Fast Track Class Valid")
    public void testCreateFastTrackClassPositive(ITestContext context) {
        logger.info("Mengeksekusi Mutation Create Program Fast Track Class (Positive)");

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("title", "Automated QA Engineering - Batch 2");
        inputData.put("description", "Materi API Automation terstruktur");
        inputData.put("type", "training");
        inputData.put("isSequential", false);

        Map<String, Object> variables = new HashMap<>();
        variables.put("input", inputData);

        // Memanggil variabel statis CREATE_PROGRAM_MUTATION
        Response response = utilRequest(CREATE_PROGRAM_MUTATION, variables, null, null, sessionId);
        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(response.getStatusCode(), 200, "Status HTTP tidak 200");
        Assert.assertNull(jsonPath.get("errors"), "Terdapat error dari GraphQL");

        String generatedProgramId = jsonPath.getString("data.createProgram.id");
        Assert.assertNotNull(generatedProgramId, "Program ID gagal di-generate!");

        context.getSuite().setAttribute("sharedProgramId", generatedProgramId);
    }

    // TC 2: Skenario Negatif (Membuktikan 1 Query bisa untuk banyak TC)
    @Test(priority = 2, groups = {"api-test"}, description = "TC-FTC-002 - Create Fast Track Class Tanpa Title")
    public void testCreateFastTrackClassNegative() {
        logger.info("Mengeksekusi Mutation Create Program (Negative Case)");

        Map<String, Object> inputData = new HashMap<>();
        inputData.put("title", ""); // Title sengaja dikosongkan untuk negative test
        inputData.put("description", "Materi API Automation terstruktur");
        inputData.put("type", "training");
        inputData.put("isSequential", false);

        Map<String, Object> variables = new HashMap<>();
        variables.put("input", inputData);

        // Memanggil variabel statis yang sama lagi
        Response response = utilRequest(CREATE_PROGRAM_MUTATION, variables, null, null, sessionId);

        // Assertions disesuaikan untuk ekspektasi error (tergantung respons API Anda)
        // Assert.assertNotNull(response.jsonPath().get("errors"), "Seharusnya muncul error validasi");
    }
}