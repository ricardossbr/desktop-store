package src.viewers.impl.menu;

import src.exception.InvalidInputException;
import src.infra.repository.impl.ProductRepositoryImp;
import src.viewers.EventMenu;

import java.util.Scanner;

import static src.input.Input.integerInput;
import static src.viewers.ConsoleColors.printError;

public class DeleteProduct implements EventMenu {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        final ProductRepositoryImp databaseImp = new ProductRepositoryImp();
        final int id;
        try {
            id = integerInput("----DIGITE O ID DO PRODUTO!-----");
            databaseImp.deleteProduct(id);
        } catch (InvalidInputException e) {
            printError("O id do produto deve ser um numero inteiro!");
        }

    }
}
