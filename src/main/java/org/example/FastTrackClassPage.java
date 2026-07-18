package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class FastTrackClassPage extends BasePage {

    @FindBy(xpath = "//p[normalize-space()='Manage Fast Track Class']")
    private WebElement ftcTitle;

    @FindBy(xpath = "//button[normalize-space()='Add fast track class']")
    private WebElement menuAddFTCButton;

    @FindBy(xpath = "//header[normalize-space()='Add New Fast Track Class']/following::button[normalize-space()='Add Fast Track Class']")
    private WebElement menuAddFTCButtonSubmit;

    @FindBy(id = "title")
    private WebElement fieldFTCName;

    @FindBy(id = "description")
    private WebElement fieldFTCDescription;

    @FindBy(className = "chakra-toast")
    private WebElement responseToast;

    @FindBy(xpath = "//li[contains(@class,'chakra-toast')]//p")
    private List<WebElement> responseToastTexts;


    public FastTrackClassPage (WebDriver driver) {
        super(driver);
    }

    public boolean isFTCTitleDisplayed() {
        waitForElementToBeVisible(ftcTitle);
        return ftcTitle.isDisplayed();
    }

    public void clickMenuAddFTCSubmit() {
        waitForElementToBeVisible(menuAddFTCButtonSubmit);
        menuAddFTCButtonSubmit.click();
    }

    public void clickMenuAddFTC() {
        waitForElementToBeVisible(menuAddFTCButton);
        menuAddFTCButton.click();
    }

    public void fillFTCNameField(String name) {
        waitForElementToBeVisible(fieldFTCName);
        fieldFTCName.sendKeys(name);
    }

    public void fieldFTCDescription(String description) {
        waitForElementToBeVisible(fieldFTCDescription);
        fieldFTCDescription.sendKeys(description);
    }

    public String getResponsePopUpText() {
        waitForElementToBeVisible(responseToast);
        wait.until(ExpectedConditions.visibilityOfAllElements(responseToastTexts));

        return responseToastTexts.stream().map(WebElement::getText).filter(
                text -> !text.isBlank()).collect(Collectors.joining(" ")
        );
    }


}
