package pl.edu.pjatk.MPR_2_Spring.model;


//Pakiet "model" reprezentują jakieś twarde dane

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Car {
   private long id;
    private String make;
    private String color;

    @JsonIgnore
    private long identification;

    public Car(String make, String color) {
        this.make = make;
        this.color = color;
        generateHashCode();
    }

    public Car() {}

    public void generateHashCode(){
        this.identification = make.hashCode() + color.hashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;

    }

    public void setMake(String make) {
        this.make = make;
    }

    public long getIdentification() {
        return identification;
    }
}
