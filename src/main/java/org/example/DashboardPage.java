package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage{

    @FindBy(xpath = "//p[normalize-space()='Dashboard']")
    private WebElement dashboardPageTitle;

    public DashboardPage (WebDriver driver) {
        super(driver);
    }
    public boolean isDashboardTitleDisplayed() {
        waitForElementToBeVisible(dashboardPageTitle);
        return dashboardPageTitle.isDisplayed();
    }
}
