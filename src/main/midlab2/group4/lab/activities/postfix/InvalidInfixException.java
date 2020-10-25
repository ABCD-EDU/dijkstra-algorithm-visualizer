package main.midlab2.group4.lab.activities.postfix;

public class InvalidInfixException extends RuntimeException{

    InvalidInfixException() {
        super();
    }

    InvalidInfixException(String s) {
        super(s);
    }

}
