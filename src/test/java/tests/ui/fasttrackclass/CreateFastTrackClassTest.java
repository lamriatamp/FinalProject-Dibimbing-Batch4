package tests.ui.fasttrackclass;

import core.BaseTest;
import core.DriverManager;
import core.TestDataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DashboardPage;
import org.example.FastTrackClassPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CreateFastTrackClassTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(CreateFastTrackClassTest.class);
    private FastTrackClassPage fastTrackClassPage;
    private DashboardPage dashboardPage;
    private String ftcName;
    private  String ftcDescription;

    @BeforeMethod
    public void setup(){
        logger.info("Pre-Condition: User sudah melakukan login");
        preConditionLogin();

        ftcName = TestDataReader.getValue("ftc-name");
        ftcDescription = TestDataReader.getValue("ftc-description");
    }

    @Test(priority = 2, groups = {"ui-test"}, description = "TC-AUTH-001 - Menguji pembuatan Fast Track Class dengan data valid")
    public void testCreateFastTrackClass() {

        // Test Steps
        logger.info("TS-1: Klik Menu Fast Track Class");
        dashboardPage = new DashboardPage(DriverManager.getDriver());
        dashboardPage.clickFTCPageButton();

        logger.info("TS-2: Klik button Add fast track class");
        fastTrackClassPage = new FastTrackClassPage(DriverManager.getDriver());
        fastTrackClassPage.isFTCTitleDisplayed();
        fastTrackClassPage.clickMenuAddFTC();

        logger.info("TS-3: Pada Pop Up Add fast Track Class. Isi field Fast Track Class Name");
        fastTrackClassPage.fillFTCNameField(ftcName);
        fastTrackClassPage.fieldFTCDescription(ftcDescription);

        logger.info("TS-4: Klik tombol Add Fast Track Class");
        fastTrackClassPage.clickMenuAddFTCSubmit();
        logger.info("Pop-up sukses menampilkan Success create program ");
        Assert.assertTrue(fastTrackClassPage.getResponsePopUpText().contains("Success create program"), "Pesan Sukses Tidak Valid");

        logger.info("executed successfully");
    }
}
