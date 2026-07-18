package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DashboardPage;
import org.example.LoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class BaseTest {
    protected static Properties config;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() {
        String env = System.getProperty("env");
        env = (env == null || env.isEmpty()) ? "stage" : env;
        config = ConfigReader.loadProperties(env);
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        DriverManager.initDriver(browser);
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(config.getProperty("baseUrl"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    protected void preConditionLogin (){
        logger.info("Pre-Condition: User sudah memiliki akun");

        // Test Steps
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        String email = config.getProperty("validEmailLogin");
        loginPage.fillEmailField(email);
        String pass = config.getProperty("validPasswordLogin");
        loginPage.fillPassField(pass);
        loginPage.clickSigninButton();
        DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());
        Assert.assertTrue(dashboardPage.isDashboardTitleDisplayed(), "Judul Dashbord Tidak Sesuai");
        String url = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("/dashboard"), "Halaman seharusnya berada di dashboard");
    }


}
