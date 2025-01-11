package pl.edu.pjatk.MPR_2_Spring.Selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddFormTest {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        this.webDriver = new ChromeDriver();
//        this.webDriver = new FirefoxDriver();
//        this.webDriver = new EdgeDriver();
    }

    @Test
    public void testAddForm(){
        AddFormPage addFormPage = new AddFormPage(webDriver);

        addFormPage.open()
                .fillInMakeInput("Opel")
                .fillInColorInput("Blue");
        ViewAllPage viewAllPage = addFormPage.submitForm();

        assertTrue(viewAllPage.isHeaderVisible());
    }
}
