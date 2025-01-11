package pl.edu.pjatk.MPR_2_Spring.exception;

public class CarsNotFoundException extends RuntimeException {
    public CarsNotFoundException() {
        super("Cars not found!");
    }
}
