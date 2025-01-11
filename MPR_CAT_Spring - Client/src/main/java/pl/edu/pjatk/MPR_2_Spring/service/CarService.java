package pl.edu.pjatk.MPR_2_Spring.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.edu.pjatk.MPR_2_Spring.exception.CarAlreadyExistException;
import pl.edu.pjatk.MPR_2_Spring.exception.CarNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.CarsNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.WrongFormatException;
import pl.edu.pjatk.MPR_2_Spring.model.Car;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;


//Pakiet "services" - dodajemy do niego wszelkie komponenty, które coś obsługują
//Przykład - kucharz w restauracji
@Service
public class CarService {

    private void initializeCars() {
        add(new Car("Ford", "Blue"));
        add(new Car("Ferrari", "Red"));
        add(new Car("Fiat", "White"));
    }

    RestClient restClient = RestClient.create("http://localhost:8081");

    public void add(Car car) {
        Car article = new Car("Ford", "Blue");
        ResponseEntity<Void> response = restClient.post()
                .uri("/articles")
                .contentType(APPLICATION_JSON)
                .body(article)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(Car car) {
        ResponseEntity<Void> response = restClient.delete()  // Używamy DELETE, nie POST
                    .uri(uriBase + "/articles/" + car.getId())  // Przekazujemy carId w ścieżce URL
                    .retrieve()
                    .toBodilessEntity();
    }

    public List<Car> getCarsList() {
        ResponseEntity<List<Car>> response = webClient.get()
                .uri("/client/capybara/all")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Capybara>>() {
                })
                .block();
        return response.getBody();
    }

    public List<Car> getCarsByMake(String make) {
        List<Car> cars = repository.findByMake(make);

        if (cars.isEmpty()) {
            throw new CarsNotFoundException();
        }

        cars.forEach(stringUtilsService::goToLowerCaseExceptFirstLetter);
        return cars;
    }

    public List<Car> getCarsByColor(String color) {
        List<Car> cars = repository.findByColor(color);

        if (cars.isEmpty()) {
            throw new CarsNotFoundException();
        }

        cars.forEach(stringUtilsService::goToLowerCaseExceptFirstLetter);
        return cars;
    }

    public Car getCar(long id) {
        Car car = restClient.get()
                .uri(uriBase + "/articles/1")
                .retrieve()
                .body(Car.class);
    }

    public void update(long id, Car newCar) {
        Optional<Car> existingCarOptional = repository.findById(id);
        if (existingCarOptional.isPresent()) {
            verifyUpdate(existingCarOptional.get(), newCar);
            repository.save(existingCarOptional.get());
        } else {
            throw new CarNotFoundException();
        }
    }

    private void verifyUpdate(Car existingCar, Car newCar) {
        if (sameNameSameColor(existingCar, newCar) || isEmptyString(newCar)) {
            throw new WrongFormatException();
        }

        existingCar.setColor(newCar.getColor());
        existingCar.setMake(newCar.getMake());
    }

    private boolean sameNameSameColor(Car c1, Car c2) {
        return c1.getColor().equals(c2.getColor()) &&
                c1.getMake().equals(c2.getMake());
    }

    private boolean isEmptyString(Car newCar) {
        String make = newCar.getMake().trim();
        String color = newCar.getColor().trim();
        return make.isEmpty() || color.isEmpty();
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

        Car car = this.restClient.getBy(id).get();
        String[] fields = {
                "ID: " + car.getId(),
                "Color: " + car.getColor(),
                "Model: " + car.getMake(),
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
