package pl.edu.pjatk.MPR_2_Spring.exception;

public class CatAlreadyExistException extends RuntimeException {
    public CatAlreadyExistException() {
        super("Cat already exist!");
    }
}
