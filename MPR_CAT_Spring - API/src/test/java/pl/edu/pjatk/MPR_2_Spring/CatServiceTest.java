package pl.edu.pjatk.MPR_2_Spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.edu.pjatk.MPR_2_Spring.exception.CatAlreadyExistException;
import pl.edu.pjatk.MPR_2_Spring.exception.CatNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.CatsNotFoundException;
import pl.edu.pjatk.MPR_2_Spring.exception.WrongFormatException;
import pl.edu.pjatk.MPR_2_Spring.model.Cat;
import pl.edu.pjatk.MPR_2_Spring.repository.CatRepository;
import pl.edu.pjatk.MPR_2_Spring.service.CatService;
import pl.edu.pjatk.MPR_2_Spring.service.StringUtilsService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CatServiceTest {
    @Mock
    private CatRepository catRepository;
    @InjectMocks
    private CatService catService;
    @Mock
    private StringUtilsService stringUtilsService;

    @BeforeEach
    public void setUp() {
        this.stringUtilsService = Mockito.mock(StringUtilsService.class);
        this.catRepository = Mockito.mock(CatRepository.class);
        this.catService = new CatService(catRepository, stringUtilsService);
        Mockito.clearInvocations(stringUtilsService);
    }


    @Test
    public void addCatWhenCarIsNewTest() {
        Cat cat = new Cat("FIAT", "6 Years", "Female", "Black");
        catService.add(cat);
        verify(stringUtilsService, times(1)).goToUpperCase(cat);
        verify(stringUtilsService, times(0)).goToLowerCaseExceptFirstLetter(cat);
    }

    @Test
    public void addCatWhenCatAlreadyExistTest() {
        Cat cat = new Cat("FIAT", "6 Years", "Female", "Black");

        when(catRepository.existsCatByIdentification(cat.getIdentification())).thenReturn(true);

        assertThrows(CatAlreadyExistException.class, () -> catService.add(cat));
    }

    @Test
    public void addCatWhenCatWrongFormatIsWrong(){
        Cat cat = new Cat("   ", "   ","   ", "   " );
        when(catRepository.existsCatByIdentification(cat.getIdentification())).thenReturn(false);
        assertThrows(WrongFormatException.class, () -> catService.add(cat));
    }

    @Test
    public void deleteCatWhenCatExistTest() {
        when(catRepository.existsById(1L)).thenReturn(true);
        catService.delete(1L);
        verify(catRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteCatWhenCatDoesntExistTest() {
        when(catRepository.existsById(4L)).thenReturn(false);
        assertThrows(CatNotFoundException.class, () -> catService.delete(4L));
    }

    @Test
    public void getCatsListWithResultTest() {
        when(this.catRepository.findAll()).thenReturn(List.of(new Cat("Daffy", "1 Years", "Female", "Black"),
                new Cat("Kitka", "5 Years 7 Months", "Male", "Gray"), new Cat("Puszek", "3 Months", "Female", "Brown")));
        catService.getCatsList();
        verify(stringUtilsService, times(3)).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(0)).goToUpperCase(any());
    }

    @Test
    void getCatsListWithNoResultTest() {
        when(this.catRepository.findAll()).thenReturn(List.of());
        assertThrows(CatsNotFoundException.class, () -> catService.getCatsList());
    }

    @Test
    public void getCatsByNameWithResultTest() {
        when(this.catRepository.findByName("Kitka")).thenReturn(List.of(new Cat("Kitka", "5 Years 7 Months", "Male", "Gray"),
                new Cat("Daffy", "1 Years", "Female", "Black")));
        catService.getCatsByName("Daffy");
        verify(stringUtilsService, times(2)).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(0)).goToUpperCase(any());
    }

    @Test
    void getCatsByNameWithNoResultTest() {
        when(this.catRepository.findByName("Boczek")).thenReturn(List.of());
        assertThrows(CatsNotFoundException.class, () -> catService.getCatsByName("Boczek"));
    }

    @Test
    public void getCatsListByRaceWithResultTest() {
        when(this.catRepository.findByRace("Black"))
                .thenReturn(List.of(new Cat("Daffy", "1 Years", "Female", "Black"), new Cat("Negata", "2 Years", "Male", "Black")));
        catService.getCatsByRace("Blue");
        verify(stringUtilsService, times(2)).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(0)).goToUpperCase(any());
    }

    @Test
    void getCatsByRaceWithNoResultTest() {
        when(this.catRepository.findByRace("Brown")).thenReturn(List.of());
        assertThrows(CatsNotFoundException.class, () -> catService.getCatsByRace("Brown"));
    }

    @Test
    public void getCatWhenIdExist() {
        when(catRepository.findById(1L)).thenReturn(Optional.of(new Cat("Kitka", "5 Years 7 Months", "Male", "Gray")));
        catService.getCat(1);
        verify(stringUtilsService, times(1)).goToLowerCaseExceptFirstLetter(any());
        verify(stringUtilsService, times(0)).goToUpperCase(any());
    }

    @Test
    public void getCatWhenIdDoesntExist() {
        when(catRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CatNotFoundException.class, () -> catService.getCat(1));
    }

    @Test
    public void updateCatTest() {
        Cat existingCat = new Cat("Kitka", "5 Years 7 Months", "Male", "Gray");
        Cat newCat = new Cat("Misiek", "8 Years 1 Months", "Male", "Ginger");

        when(catRepository.findById(1L)).thenReturn(Optional.of(existingCat));
        catService.update(1, newCat);
        assertEquals(existingCat.getIdentification(),newCat.getIdentification());
    }

    @Test
    public void updateCatWhenCatDoesntExist(){
        when(catRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CatNotFoundException.class, () -> catService.update(1L,new Cat("Misiek", "8 Years 1 Months", "Male", "Ginger")));
    }

    @Test
    public void updateCatWhenCatNameAndRaceAreTheSameTest(){
        Cat existingCat = new Cat("Misiek", "8 Years 1 Months", "Male", "Ginger");
        Cat newCat = new Cat("Misiek", "8 Years 1 Months", "Male", "Ginger");

        when(catRepository.findById(1L)).thenReturn(Optional.of(existingCat));
        assertThrows(WrongFormatException.class, () -> catService.update(1L,newCat));
    }

    @Test
    public void updateCatWhenCatParamsAreEmptyStrings(){
        Cat existingCat = new Cat("Misiek", "8 Years 1 Months", "Male", "Ginger");
        Cat newCat = new Cat("   ", "   ","   ", "   " );

        when(catRepository.findById(1L)).thenReturn(Optional.of(existingCat));
        assertThrows(WrongFormatException.class, () -> catService.update(1L,newCat));
    }


}
