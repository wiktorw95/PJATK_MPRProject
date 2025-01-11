package pl.edu.pjatk.MPR_2_Spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjatk.MPR_2_Spring.model.Cat;

import java.util.List;

@Repository
public interface CatRepository extends CrudRepository<Cat, Long> {
    List<Cat> findByName(String name);
    List<Cat> findByAge(String age);
    List<Cat> findByGender(String gender);
    List<Cat> findByRace(String race);
    boolean existsCatByIdentification(long identification);
}
