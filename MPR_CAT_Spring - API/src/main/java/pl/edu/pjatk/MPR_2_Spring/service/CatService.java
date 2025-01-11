package pl.edu.pjatk.MPR_2_Spring.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import pl.edu.pjatk.MPR_2_Spring.exception.CatAlreadyExistException;
import pl.edu.pjatk.MPR_2_Spring.exception.CatNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.CatsNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.WrongFormatException;
import pl.edu.pjatk.MPR_2_Spring.model.Cat;
import pl.edu.pjatk.MPR_2_Spring.repository.CatRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

//Pakiet "services" - dodajemy do niego wszelkie komponenty, które coś obsługują
//Przykład - kucharz w restauracji
@Service
public class CatService {
    private final CatRepository repository;
    private final StringUtilsService stringUtilsService;

    public CatService(CatRepository repository, StringUtilsService stringUtilsService) {
        this.repository = repository;
        this.stringUtilsService = stringUtilsService;
        initializeCats();
    }

    private void initializeCats() {
        add(new Cat("Fluff", "4 Years", "Female", "Blue"));
        add(new Cat("JIMMY", "9 Months", "Male", "Brown"));
        add(new Cat("BoNiFaCy", "3 Years 2 Months", "Female", "Blue"));
    }

    public void add(Cat cat) {
        cat.generateHashCode();
        if (repository.existsCatByIdentification(cat.getIdentification())) {
            throw new CatAlreadyExistException();
        }

        if(isEmptyString(cat)){
            throw new WrongFormatException();
        }

        stringUtilsService.goToUpperCase(cat);
        repository.save(cat);
    }

    public void delete(long id) {
        if (!repository.existsById(id)) {
            throw new CatNotFoundException();
        }
        repository.deleteById(id);
    }

    public List<Cat> getCatsList() {
        List<Cat> cats = (List<Cat>) repository.findAll();

        if (cats.isEmpty()) {
            throw new CatsNotFoundException();
        }

        cats.forEach(stringUtilsService::goToLowerCaseExceptFirstLetter);
        return cats;
    }

    public List<Cat> getCatsByName(String name) {
        List<Cat> cats = repository.findByName(name);

        if (cats.isEmpty()) {
            throw new CatsNotFoundException();
        }

        cats.forEach(stringUtilsService::goToLowerCaseExceptFirstLetter);
        return cats;
    }

    public List<Cat> getCatsByGender(String gender) {
        List<Cat> cats = repository.findByGender(gender);

        if (cats.isEmpty()) {
            throw new CatsNotFoundException();
        }

        cats.forEach(stringUtilsService::goToLowerCaseExceptFirstLetter);
        return cats;
    }
    public List<Cat> getCatsByRace(String race) {
        List<Cat> cats = repository.findByRace(race);

        if (cats.isEmpty()) {
            throw new CatsNotFoundException();
        }

        cats.forEach(stringUtilsService::goToLowerCaseExceptFirstLetter);
        return cats;
    }

    public Cat getCat(long id) {
        Optional<Cat> cat = repository.findById(id);

        if (cat.isEmpty()) {
            throw new CatNotFoundException();
        }

        stringUtilsService.goToLowerCaseExceptFirstLetter(cat.get());

        return cat.get();
    }

    public void update(long id, Cat newCat) {
        Optional<Cat> existingCatOptional = repository.findById(id);
        if (existingCatOptional.isPresent()) {
            verifyUpdate(existingCatOptional.get(), newCat);
            repository.save(existingCatOptional.get());
        } else {
            throw new CatNotFoundException();
        }
    }

    private void verifyUpdate(Cat existingCat, Cat newCat) {
        if (sameNameSameColor(existingCat, newCat) || isEmptyString(newCat)) {
            throw new WrongFormatException();
        }

        existingCat.setName(newCat.getName());
        existingCat.setAge(newCat.getAge());
        existingCat.setGender(newCat.getGender());
        existingCat.setRace(newCat.getRace());
    }

    private boolean sameNameSameColor(Cat c1, Cat c2) {
        return c1.getName().equals(c2.getName());
    }

    private boolean isEmptyString(Cat newCat) {
        String name = newCat.getName().trim();
        String age = String.valueOf(newCat.getAge());
        String gender = newCat.getGender().trim();
        String race = newCat.getRace().trim();
        return name.isEmpty() || age.isEmpty() || gender.isEmpty() || race.isEmpty();
    }
    public byte[] generatePdf(Long id) throws IOException {
        // Tworzenie nowego dokumentu PDF
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        // Tworzenie strumienia zawartości strony PDF
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Wstawianie danych o samochodzie do PDF
        int yStart = 750;
        int lineHeight = 14;
        int margin = 50;

        Cat cat = this.repository.findById(id).get();
        String[] fields = {
                "ID: " + cat.getId(),
                "Name: " + cat.getName(),
                "Age: " + cat.getAge(),
                "Gender: " + cat.getGender(),
                "Race: " + cat.getRace(),
        };

        // Rysowanie tekstu na stronie PDF
        for (String field : fields) {
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yStart);
            contentStream.showText(field);
            contentStream.endText();
            yStart -= lineHeight;
        }

        // Zamknięcie strumienia zawartości
        contentStream.close();

        // Zapisanie dokumentu PDF do strumienia
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save("myPdf.pdf");
        document.close();

        // Zwrócenie zawartości PDF w postaci tablicy bajtów
        return byteArrayOutputStream.toByteArray();
    }
}
