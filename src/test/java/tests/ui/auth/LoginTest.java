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
    //atribut global
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
    @Test(priority = 3, groups = {"ui-test"}, description = "TC-AUTH-002 - Menguji proses login menggunakan Email terdaftar namun dengan Password yang salah.")
    public void testLoginWithInvalidPassword() {
        logger.info("Pre-Condition: User sudah memiliki akun");

        logger.info("TS-1: Masuk kehalaman Login");
        loginPage = new LoginPage(DriverManager.getDriver());

        logger.info("TS-2: Masukkan Email valid");
        String email = config.getProperty("validEmailLogin");
        loginPage.fillEmailField(email);

        logger.info("TS-3: Masukkan Password salah");
        String invalidPass = "Salah12345!";
        loginPage.fillPassField(invalidPass);

        logger.info("TS-4: Klik tombol Login");
        loginPage.clickSigninButton();

        logger.info("Expected Result: Sistem berhasil menolak akses dan menampilkan pesan error 'wrong username or password'");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),"Pesan Gagal Tidak Tampil");

        String url = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("/login"), "Halaman seharusnya tetap berada di Login, bukan di Dashboard");

        logger.info("TC-AUTH-002 executed successfully");
    }

    @Test(priority = 3, groups = {"ui-test"}, description = "TC-AUTH-002 - Menguji fungsionalitas ikon (eye icon)")

    public void testHidePassword() {

        logger.info("Pre-Condition: User sudah memiliki akun");

        logger.info("TS-1: Masuk kehalaman Login");
        loginPage = new LoginPage(DriverManager.getDriver());

        logger.info("TS-2: Masukkan Email valid");
        String email = config.getProperty("validEmailLogin");
        loginPage.fillEmailField(email);
        String previousPasswordType = loginPage.currentPasswordType();
        Assert.assertTrue(previousPasswordType.equals("password"), "Type input tidak sesuai");

        logger.info("TS-4: Klik icon eye");
        loginPage.clickIconHidePassword();
        String currentPasswordType = loginPage.currentPasswordType();
        Assert.assertTrue(currentPasswordType.equals("text"), "Type input tidak sesuai");

        logger.info("TC-AUTH-007 executed successfully");
    }

}
