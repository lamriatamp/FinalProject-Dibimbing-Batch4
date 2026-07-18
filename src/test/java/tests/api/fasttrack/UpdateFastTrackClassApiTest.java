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

public class UpdateFastTrackClassApiTest extends BaseApiTest {
    private static final Logger logger = LogManager.getLogger(UpdateFastTrackClassApiTest.class);
    private String sessionId;

    private static final String UPDATE_PROGRAM_MUTATION = "mutation updateProgram($id: String!, $input: ProgramInput!) {\n" +
            "  updateProgram(id: $id, input: $input) {\n" +
            "    id\n" +
            "    __typename\n" +
            "  }\n" +
            "}";

    @BeforeClass(alwaysRun = true)
    public void setupAuth() {
        logger.info("Pre-Condition: Mendapatkan Session Cookie untuk Update FTC");
        sessionId = TestUtils.getSid();
        Assert.assertNotNull(sessionId, "Session ID kosong, gagal login!");
    }

    @Test(priority = 1, groups = {"api-test"}, description = "TC-FTC-004 - Update Fast Track Class Valid")
    public void testUpdateFastTrackClassPositive(ITestContext context) {
        // 1. Ambil ID yang dibuat oleh kelas Create
        String programId = (String) context.getSuite().getAttribute("sharedProgramId");

        if (programId == null) {
            throw new SkipException("Test di-skip karena sharedProgramId tidak ditemukan.");
        }

        logger.info("Mengeksekusi Mutation Update Program untuk ID: " + programId);

        // 2. Susun isi dari "input" (Data yang ingin diubah)
        Map<String, Object> inputData = new HashMap<>();
        inputData.put("title", "Automated QA Engineering - Batch 2 (UPDATED)");
        inputData.put("description", "Deskripsi telah diubah melalui API Automation");
        inputData.put("type", "training");
        inputData.put("isSequential", false);

        // 3. Susun variabel utama yang memuat "id" dan "input"
        Map<String, Object> variables = new HashMap<>();
        variables.put("id", programId);
        variables.put("input", inputData);

        // 4. Hit API
        Response response = utilRequest(UPDATE_PROGRAM_MUTATION, variables, null, null, sessionId);
        JsonPath jsonPath = response.jsonPath();

        // 5. Validasi
        Assert.assertEquals(response.getStatusCode(), 200, "Status HTTP tidak 200");
        Assert.assertNull(jsonPath.get("errors"), "Terdapat error dari GraphQL saat Update");

        // Memastikan ID yang dikembalikan sama dengan ID yang kita kirim
        String updatedId = jsonPath.getString("data.updateProgram.id");
        Assert.assertEquals(updatedId, programId, "ID yang di-update tidak sesuai dengan request!");

        logger.info("Berhasil meng-update Program dengan ID: " + updatedId);
    }
}