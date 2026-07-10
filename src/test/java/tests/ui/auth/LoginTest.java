package tests.ui.auth;

import core.BaseTest;
import core.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DashboardPage;
import org.example.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Test(priority = 2, groups = {"ui-test"}, description = "TC-AUTH-001 - Menguji proses login menggunakan kombinasi Email dan Password yang benar.")
    public void testLoginWithValidCredentials() {
        logger.info("Pre-Condition: User sudah memiliki akun");

        // Test Steps
        logger.info("TS-1: Masuk kehalaman Login");
        loginPage = new LoginPage(DriverManager.getDriver());

        logger.info("TS-2: Masukkan Email");
        String email = config.getProperty("validEmailLogin");
        loginPage.fillEmailField(email);

        logger.info("TS-3: Masukkan Password");
        String pass = config.getProperty("validPasswordLogin");
        loginPage.fillPassField(pass);

        logger.info("TS-4: Klik tombol Login");
        loginPage.clickSigninButton();

        logger.info("Expected Result: Pengguna berhasil login, sistem mengarahkan ke halaman utama Dashboard");

        dashboardPage = new DashboardPage(DriverManager.getDriver());
        Assert.assertTrue(dashboardPage.isDashboardTitleDisplayed(), "Judul Dashbord Tidak Sesuai");
        String url = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("/dashboard"), "Halaman seharusnya berada di dashboard");

        logger.info("executed successfully");
    }

}
