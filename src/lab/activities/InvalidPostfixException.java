package lab.activities;

public class InvalidPostfixException extends RuntimeException{

    InvalidPostfixException() {
        super();
    }

    InvalidPostfixException(String s) {
        super(s);
    }

}
