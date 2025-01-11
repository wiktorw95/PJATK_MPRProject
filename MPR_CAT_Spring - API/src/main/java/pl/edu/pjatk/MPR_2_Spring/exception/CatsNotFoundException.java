package pl.edu.pjatk.MPR_2_Spring.exception;

public class CatsNotFoundException extends RuntimeException {
    public CatsNotFoundException() {
        super("Cats not found!");
    }
}
