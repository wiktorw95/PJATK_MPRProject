package pl.edu.pjatk.MPR_2_Spring.service;

import org.springframework.stereotype.Component;
import pl.edu.pjatk.MPR_2_Spring.model.Cat;

@Component
public class StringUtilsService {
    public void goToUpperCase(Cat cat) {
        cat.setMake(cat.getMake().toUpperCase());
        cat.setColor(cat.getColor().toUpperCase());
    }

    public void goToLowerCaseExceptFirstLetter(Cat cat) {
        cat.setMake(cat.getMake().toLowerCase());
        cat.setColor(cat.getColor().toLowerCase());

        cat.setMake(cat.getMake().substring(0, 1).toUpperCase() + cat.getMake().substring(1));
        cat.setColor(cat.getColor().substring(0, 1).toUpperCase() + cat.getColor().substring(1));
    }
}
