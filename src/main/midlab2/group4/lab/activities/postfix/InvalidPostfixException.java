package main.midlab2.group4.lab.activities.postfix;

public class InvalidPostfixException extends RuntimeException{

    InvalidPostfixException() {
        super();
    }

    InvalidPostfixException(String s) {
        super(s);
    }

}
