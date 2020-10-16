package lab.activities.postfix;

public class InvalidInfixException extends RuntimeException{

    InvalidInfixException() {
        super();
    }

    InvalidInfixException(String s) {
        super(s);
    }

}
