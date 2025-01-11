package src.domain.usecase;

import src.domain.adapters.IEditProduct;
import src.domain.model.Product;
import src.exception.InvalidInputException;
import src.infra.adapters.ProductRepositoryAdapter;
import src.infra.repository.ProductRepositoryImp;

import java.math.BigDecimal;
import java.util.Optional;

import static src.application.input.Input.*;
import static src.application.input.ConsoleColors.*;

public class EditProductUseCase implements IEditProduct {
    private final ProductRepositoryAdapter repository = new ProductRepositoryImp();

    public void execute() {
        try {
            final int id = integerInput("----DIGITE O ID DO PRODUTO!-----");
            final Optional<Product> productFound = repository.getById(id);
            if(productFound.isPresent()) {
                final Product product = productFound.get();
                final String name = stringInput("DIGITE O NOME DO PRODUTO");
                final BigDecimal value = bigDecimalInput("DIGITE O VALOR DO PRODUTO");
                final int quantity = integerInput("DIGITE A QUANTIDADE DO PRODUTO");
                if(checkUserConfirmed("Tem certeza que deseja editar o produto?")){
                    product.prepareToEdit(name, value, quantity);
                    repository.editProduct(product);
                    printMessage("Produto editado com sucesso!");
                }
                return;
            }
            printError("id do produto não foi encontrado!");
        } catch (InvalidInputException e) {
            if(e.getMessage().contains("String")){
                printError("Nome deve ser preenchido com letras!");
            }

            if(e.getMessage().contains("Integer")){
                printError("A quantidade valor inteiro!");
            }

            printError("Para o campo valor deve ser ponto flutuante");
        }

    }

    private boolean checkUserConfirmed(String message) throws InvalidInputException {
        printInfo(message);
        String confirmed = stringInput(message);
        return confirmed.trim().equals("sim");
    }
}
