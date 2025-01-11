package src.domain.usecase;

import src.domain.adapters.IDeleteProduct;
import src.exception.InvalidInputException;
import src.infra.adapters.ProductRepositoryAdapter;
import src.infra.repository.ProductRepositoryImp;

import static src.application.input.Input.integerInput;
import static src.application.input.ConsoleColors.printError;

public class DeleteProductUseCase implements IDeleteProduct {
    private final ProductRepositoryAdapter repository = new ProductRepositoryImp();

    public void execute() {
        try {
            final int id = integerInput("----DIGITE O ID DO PRODUTO!-----");
            repository.deleteProduct(id);
        } catch (InvalidInputException e) {
            printError("O id do produto deve ser um numero inteiro!");
        }
    }
}
