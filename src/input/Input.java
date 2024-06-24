package src.input;

import src.exception.InvalidInputException;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

    public static Integer integerInput(String messageToPrint) throws InvalidInputException {
        System.out.println(messageToPrint);
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        }catch (InputMismatchException e){
            throw new InvalidInputException("Error, Should be a Integer");
        }

    }

    public static Double doubleInput(String messageToPrint) throws InvalidInputException {
        System.out.println(messageToPrint);
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextDouble();
        }catch (InputMismatchException e){
            throw new InvalidInputException("Error, Should be a Double");
        }

    }

    public static String stringInput(String messageToPrint) throws InvalidInputException {
        System.out.println(messageToPrint);
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextLine();
        }catch (InputMismatchException e){
            throw new InvalidInputException("Error, Should be a String");
        }
    }

    public static BigDecimal bigDecimalInput(String messageToPrint) throws InvalidInputException {
        System.out.println(messageToPrint);
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextBigDecimal();
        }catch (InputMismatchException e){
            throw new InvalidInputException("Error, Should be a BigDecimal");
        }
    }
}
