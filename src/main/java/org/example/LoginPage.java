package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(id = "input-username-or-email")
    private WebElement emailInput;

    @FindBy(id = "input-password")
    private WebElement passInput;

    @FindBy(id = "button-sign-in")
    private WebElement signInButton;

    @FindBy(xpath = "//p[normalize-space()='wrong username or password']")
    private WebElement failedMessageWrongPassword;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void fillEmailField(String email) {
        waitForElementToBeVisible(emailInput);
        emailInput.sendKeys(email);
    }

    public void fillPassField(String pass) {
        waitForElementToBeVisible(passInput);
        passInput.sendKeys(pass);
    }

    public void clickSigninButton() {
        waitForElementToBeVisible(signInButton);
        signInButton.click();
    }

    public boolean isErrorMessageDisplayed() {
        waitForElementToBeVisible(failedMessageWrongPassword);
        return failedMessageWrongPassword.isDisplayed();
    }
}
