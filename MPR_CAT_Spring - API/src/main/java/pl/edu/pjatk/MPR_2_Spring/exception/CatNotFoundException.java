package pl.edu.pjatk.MPR_2_Spring.exception;

public class CatNotFoundException extends RuntimeException {
    public CatNotFoundException() {
        super("Cat not found!");
    }
}
