package pl.edu.pjatk.MPR_2_Spring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Pakiet "model" reprezentują jakieś twarde dane
@Entity
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String age;
    private String gender;
    private String race;
    private long identification;

    public Cat(String name, String age, String gender, String race) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.race = race;
    }


    public Cat() {}

    public void generateHashCode(){
        this.identification = name.hashCode() + race.hashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getIdentification() {
        return identification;
    }

    @Override
    public String toString() {return "Cat{name='" + name + '\'' + ", age='" + age + '\'' + ", gender='" + gender + '\'' + ", race='" + race + '\'' + ", identification=" + identification + '}';}
}
