package lab.activities;

public class InvalidInfixException extends RuntimeException{

    InvalidInfixException() {
        super();
    }

    InvalidInfixException(String s) {
        super(s);
    }

}
