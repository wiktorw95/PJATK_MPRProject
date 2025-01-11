package pl.edu.pjatk.MPR_2_Spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_2_Spring.model.Cat;
import pl.edu.pjatk.MPR_2_Spring.service.CatService;

import java.io.IOException;
import java.util.List;

//Ta adnotacja będzie się komunikowała z siecią (Wystawiamy ją na sieć)
//Dostajemy żądanie HTTP od klienta i kontroler je odbiera. Kontroler jedynie odsyła logikę do dalszych komponentów.

@RestController
public class MyRestController {

    @Autowired
    private final CatService catService;

    @Autowired   //Autowired - dzięki niemu spring tworzy automatycznie nowy obiekt, kiedy go potrzebuje
    public MyRestController(CatService catService) {
        this.catService = catService;
    }

    //Przeglądarka zawsze działa na GET i POST
    //GET - żądamy o zasób
    //POST - wsadzamy nowe dane
    //JSON to zazwyczaj klucz wartość
    //Struktura JSON'a:
    //{
    //  klucz = wartość
    //}

    //GET
    @GetMapping("cats/all")
    public ResponseEntity<Iterable<Cat>> getAll() {
        return new ResponseEntity<>(this.catService.getCatsList(), HttpStatus.OK);
    }

    @GetMapping("cats/name/{name}")
    public ResponseEntity<List<Cat>> getAllByName(@PathVariable String name) {
        //podnosimy pierwsza litere
        return new ResponseEntity<>(this.catService.getCatsByName(name.substring(0,1).toUpperCase()
                + name.substring(1)), HttpStatus.OK);
    }

    @GetMapping("cats/gender/{gender}")
    public ResponseEntity<List<Cat>> getAllByGender(@PathVariable String gender) {
        //podnosimy pierwsza litere
        return new ResponseEntity<>(this.catService.getCatsByGender(gender.substring(0,1).toUpperCase()
                + gender.substring(1)), HttpStatus.OK);
    }

    //GET
    @GetMapping("cats/{id}")
    public ResponseEntity<Cat> get(@PathVariable Integer id) {
        return new ResponseEntity<>(this.catService.getCat(id), HttpStatus.OK);
    }

//    //POST - będziemy tu dodawać nowe obiekty typu Car
//    @PostMapping("cars")
//    public ResponseEntity<Void> add(@RequestBody Car car) {
//        this.carService.add(car);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @DeleteMapping("cats/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        this.catService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("cats/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Cat cat) {
        this.catService.update(id, cat);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("cats/{id}/pdf")
    public ResponseEntity<byte[]> getById(@PathVariable Long id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "inline; filename=cat_" + id + "_details.pdf");
        this.catService.generatePdf(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
