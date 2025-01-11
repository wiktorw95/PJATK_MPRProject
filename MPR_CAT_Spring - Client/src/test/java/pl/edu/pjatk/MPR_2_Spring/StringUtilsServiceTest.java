package pl.edu.pjatk.MPR_2_Spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
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
        Car car = new Car("FIAT", "WHITE");
        stringUtilsService.goToUpperCase(car);
        assertEquals("FIAT", car.getMake());
        assertEquals("WHITE", car.getColor());
    }

    @Test
    public void goToUpperCaseWhenAllLettersAreLowerCase() {
        Car car = new Car("fiat", "white");
        stringUtilsService.goToUpperCase(car);
        assertEquals("FIAT", car.getMake());
        assertEquals("WHITE", car.getColor());
    }

    @Test
    public void goToUpperCaseWhenAllLettersAreMixCase() {
        Car car = new Car("FiaT", "WhiTe");
        stringUtilsService.goToUpperCase(car);
        assertEquals("FIAT", car.getMake());
        assertEquals("WHITE", car.getColor());
    }

    @Test
    public void goToLowerCaseExceptFirstLetterWhenAllLettersAreUpperCase() {
        Car car = new Car("FIAT", "WHITE");
        stringUtilsService.goToLowerCaseExceptFirstLetter(car);
        assertEquals("Fiat", car.getMake());
        assertEquals("White", car.getColor());
    }

    @Test
    public void goToLowerCaseExceptFirstLetterWhenAllLettersAreLowerCase() {
        Car car = new Car("fiat", "white");
        stringUtilsService.goToLowerCaseExceptFirstLetter(car);
        assertEquals("Fiat", car.getMake());
        assertEquals("White", car.getColor());
    }

    @Test
    public void goToLowerCaseExceptFirstLetterWhenAllLettersAreMixCase() {
        Car car = new Car("fIAt", "WHiTe");
        stringUtilsService.goToLowerCaseExceptFirstLetter(car);
        assertEquals("Fiat", car.getMake());
        assertEquals("White", car.getColor());
    }
}
