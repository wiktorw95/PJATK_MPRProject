package pl.edu.pjatk.MPR_2_Spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
import pl.edu.pjatk.MPR_2_Spring.service.CarService;

@Controller
public class MyViewController {

    private CarService carService;

    @Autowired
    public MyViewController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("view/all")
    public String viewAllCars(Model model) {
        model.addAttribute("carList", carService.getCarsList());
        return "viewAll";
    }

    @GetMapping("addForm")
    public String displayAddForm(Model model) {
        model.addAttribute("car", new Car());
        return "addForm";
    }

    @PostMapping("addForm")
    public String submitAddForm(@ModelAttribute Car car) {  // @ModelAttribute działa jak @RequestBody ale bierze z formularza zamiast strony
        this.carService.add(car);
        return "redirect:http://localhost:8080/view/all";
    }
    // Wyświetlenie formularza edycji samochodu
    @GetMapping("editForm")
    public String displayEditForm(Model model) {
        model.addAttribute("carList", carService.getCarsList());
        model.addAttribute("car", new Car());  // Dodajemy samochód do modelu
        return "editForm";  // Zwracamy widok formularza edycji
    }

    // Przesyłanie formularza i aktualizacja danych
    @PostMapping("editForm")
    public String submitEditForm(@ModelAttribute Car car) {
        carService.update(car.getId(), car);  // Aktualizujemy dane samochodu
        return "redirect:http://localhost:8080/view/all";  // Przekierowanie do widoku listy samochodów
    }


}
