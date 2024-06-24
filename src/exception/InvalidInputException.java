package src.exception;

public class InvalidInputException extends Exception {

    public InvalidInputException(){
        super("Input is invalid!");
    }

    public InvalidInputException(String message){
        super(message);
    }
}

