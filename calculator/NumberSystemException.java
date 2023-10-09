package calculator;

public class NumberSystemException extends IncorrectInputException {
    NumberSystemException() {
        super();
    }

    NumberSystemException(String message) {
        super(message);
    }
}

class IncorrectInputException extends IllegalArgumentException {
    IncorrectInputException() {
        super("incorrect input");
    }

    IncorrectInputException(String message) {
        super("incorrect input: " + message);
    }
}


