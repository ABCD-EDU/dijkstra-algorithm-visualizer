package lab.util;

public class ListOverflowException extends RuntimeException {
    public ListOverflowException() {
        super();
    }
    
    public ListOverflowException(String s) {
        super(s);
    }
}
