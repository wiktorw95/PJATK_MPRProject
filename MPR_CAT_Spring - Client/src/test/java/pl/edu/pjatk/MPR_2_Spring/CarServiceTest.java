package pl.edu.pjatk.MPR_2_Spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import pl.edu.pjatk.MPR_2_Spring.controller.MyRestController;
import pl.edu.pjatk.MPR_2_Spring.exception.CarAlreadyExistException;
import pl.edu.pjatk.MPR_2_Spring.exception.CarNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.CarsNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.WrongFormatException;
import pl.edu.pjatk.MPR_2_Spring.model.Car;
import pl.edu.pjatk.MPR_2_Spring.repository.CarRepository;
import pl.edu.pjatk.MPR_2_Spring.service.CarService;
import pl.edu.pjatk.MPR_2_Spring.service.StringUtilsService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CarServiceTest {
    private CarRepository carRepository;
    private CarService carService;
    private StringUtilsService stringUtilsService;

    @BeforeEach
    public void setUp() {
        this.stringUtilsService = Mockito.mock(StringUtilsService.class);
        this.carRepository = Mockito.mock(CarRepository.class);
        this.carService = new CarService(carRepository, stringUtilsService);
        Mockito.clearInvocations(stringUtilsService);
    }


    @Test
    public void addCarWhenCarIsNewTest() {
        Car car = new Car("Opel", "Green");
        carService.add(car);
        verify(stringUtilsService, times(1)).goToUpperCase(car);
        verify(stringUtilsService, times(0)).goToLowerCaseExceptFirstLetter(car);
    }

    @Test
    public void addCarWhenCarAlreadyExistTest() {
        Car car = new Car("Opel", "Green");

        when(carRepository.existsCarByIdentification(car.getIdentification())).thenReturn(true);

        assertThrows(CarAlreadyExistException.class, () -> carService.add(car));
    }

    @Test
    public void addCarWhenCarWrongFormatIsWrong(){
        Car car = new Car("   ", "   ");
        when(carRepository.existsCarByIdentification(car.getIdentification())).thenReturn(false);
        assertThrows(WrongFormatException.class, () -> carService.add(car));
    }

    @Test
    public void deleteCarWhenCarExistTest() {
        when(carRepository.existsById(1L)).thenReturn(true);
        carService.delete(1L);
        verify(carRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteCarWhenCarDoesntExistTest() {
        when(carRepository.existsById(4L)).thenReturn(false);
        assertThrows(CarNotFoundException.class, () -> carService.delete(4L));
    }

    @Test
    public void getCarsListWithResultTest() {
        when(this.carRepository.findAll()).thenReturn(List.of(new Car("Renault", "White"),
                new Car("Opel", "Silver"), new Car("Ferrari", "Blue")));
        carService.getCarsList();
        verify(stringUtilsService, times(3)).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(0)).goToUpperCase(any());
    }

    @Test
    void getCarsListWithNoResultTest() {
        when(this.carRepository.findAll()).thenReturn(List.of());
        assertThrows(CarsNotFoundException.class, () -> carService.getCarsList());
    }

    @Test
    public void getCarsByMakeWithResultTest() {
        when(this.carRepository.findByMake("Renault")).thenReturn(List.of(new Car("Renault", "White"),
                new Car("Renault", "Blue")));
        carService.getCarsByMake("Renault");
        verify(stringUtilsService, times(2)).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(0)).goToUpperCase(any());
    }

    @Test
    void getCarsByMakeWithNoResultTest() {
        when(this.carRepository.findByMake("Ferrari")).thenReturn(List.of());
        assertThrows(CarsNotFoundException.class, () -> carService.getCarsByMake("Ferrari"));
    }

    @Test
    public void getCarsListByColorWithResultTest() {
        when(this.carRepository.findByColor("Blue"))
                .thenReturn(List.of(new Car("Renault", "Blue"), new Car("Ferrari", "Blue")));
        carService.getCarsByColor("Blue");
        verify(stringUtilsService, times(2)).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(0)).goToUpperCase(any());
    }

    @Test
    void getCarsByColorWithNoResultTest() {
        when(this.carRepository.findByColor("Blue")).thenReturn(List.of());
        assertThrows(CarsNotFoundException.class, () -> carService.getCarsByMake("Blue"));
    }

    @Test
    public void getCarWhenIdExist() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(new Car("Ferrari", "Red")));
        carService.getCar(1);
        verify(stringUtilsService, times(1)).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(0)).goToUpperCase(any());
    }

    @Test
    public void getCarWhenIdDoesntExist() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.getCar(1));
    }

    @Test
    public void updateCarTest() {
        Car existingCar = new Car("Ferrari", "Red");
        Car newCar = new Car("Alfa Romeo", "Pink");

        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));
        carService.update(1, newCar);
        assertEquals(existingCar.getIdentification(),newCar.getIdentification());
    }

    @Test
    public void updateCarWhenCarDoesntExist(){
        when(carRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.update(1L,new Car("Ferrari","Red")));
    }

    @Test
    public void updateCarWhenCarMakeAndColorAreTheSameTest(){
        Car existingCar = new Car("Alfa Romeo", "Pink");
        Car newCar = new Car("Alfa Romeo", "Pink");

        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));
        assertThrows(WrongFormatException.class, () -> carService.update(1L,newCar));
    }

    @Test
    public void updateCarWhenCarParamsAreEmptyStrings(){
        Car existingCar = new Car("Alfa Romeo", "Pink");
        Car newCar = new Car("  ","   ");

        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));
        assertThrows(WrongFormatException.class, () -> carService.update(1L,newCar));
    }


}
