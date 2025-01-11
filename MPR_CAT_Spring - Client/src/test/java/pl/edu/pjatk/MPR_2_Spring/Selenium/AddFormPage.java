package pl.edu.pjatk.MPR_2_Spring.Selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class AddFormPage {
    WebDriver webDriver;

    @FindBy(id="make")
    private WebElement makeInput;

    @FindBy(id="color")
    private WebElement colorInput;

    @FindBy(id="submit")
    private WebElement submitButton;

    public AddFormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public AddFormPage open(){
        this.webDriver.get("http://localhost:8080/addForm");
        return this;
    }

    public AddFormPage fillInMakeInput (String text){
        this.makeInput.sendKeys(text);
        return this;
    }
    public AddFormPage fillInColorInput (String text){
        this.colorInput.sendKeys(text);
        return this;
    }
    public ViewAllPage submitForm(){
        this.submitButton.click();
        return new ViewAllPage(this.webDriver);
    }

}
