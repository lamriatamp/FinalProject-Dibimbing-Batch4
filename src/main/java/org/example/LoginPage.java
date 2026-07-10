package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    @FindBy(id = "input-username-or-email")
    private WebElement emailInput;

    @FindBy(id = "input-password")
    private WebElement passInput;

    @FindBy(id = "button-sign-in")
    private WebElement signInButton;


    public LoginPage (WebDriver driver) {
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
}
