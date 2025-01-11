package pl.edu.pjatk.MPR_2_Spring.exception;

public class CarAlreadyExistException extends RuntimeException {
    public CarAlreadyExistException() {
        super("Car already exist!");
    }
}
