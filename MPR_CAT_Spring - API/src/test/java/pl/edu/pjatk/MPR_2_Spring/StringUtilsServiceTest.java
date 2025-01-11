package pl.edu.pjatk.MPR_2_Spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pjatk.MPR_2_Spring.model.Cat;
import pl.edu.pjatk.MPR_2_Spring.service.StringUtilsService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilsServiceTest {
    private StringUtilsService stringUtilsService;
    @BeforeEach
    public void setUp() {
        this.stringUtilsService = new StringUtilsService();
    }

    @Test
    public void goToUpperCaseWhenAllLettersAreUpperCase() {
        Cat cat = new Cat("FIAT", "6 Years", "Female", "Black");
        stringUtilsService.goToUpperCase(cat);
        assertEquals("FIAT", cat.getName());
        assertEquals("BLACK", cat.getRace());
    }

    @Test
    public void goToUpperCaseWhenAllLettersAreLowerCase() {
        Cat cat = new Cat("FIAT", "6 Years", "Female", "Black");
        stringUtilsService.goToUpperCase(cat);
        assertEquals("FIAT", cat.getName());
        assertEquals("BLACK", cat.getRace());
    }

    @Test
    public void goToUpperCaseWhenAllLettersAreMixCase() {
        Cat cat = new Cat("FIAT", "6 Years", "Female", "Black");
        stringUtilsService.goToUpperCase(cat);
        assertEquals("FIAT", cat.getName());
    }

    @Test
    public void goToLowerCaseExceptFirstLetterWhenAllLettersAreUpperCase() {
        Cat cat = new Cat("FIAT", "6 Years", "Female", "Black");
        stringUtilsService.goToLowerCaseExceptFirstLetter(cat);
        assertEquals("Fiat", cat.getName());
    }

    @Test
    public void goToLowerCaseExceptFirstLetterWhenAllLettersAreLowerCase() {
        Cat cat = new Cat("FIAT", "6 Years", "Female", "Black");
        stringUtilsService.goToLowerCaseExceptFirstLetter(cat);
        assertEquals("Fiat", cat.getName());
    }

    @Test
    public void goToLowerCaseExceptFirstLetterWhenAllLettersAreMixCase() {
        Cat cat = new Cat("FIAT", "6 Years", "Female", "Black");
        stringUtilsService.goToLowerCaseExceptFirstLetter(cat);
        assertEquals("Fiat", cat.getName());
    }
}
