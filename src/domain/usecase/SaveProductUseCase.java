package src.domain.usecase;

import src.domain.adapters.ISaveProduct;
import src.domain.model.Product;
import src.exception.InvalidInputException;
import src.infra.adapters.ProductRepositoryAdapter;
import src.infra.repository.ProductRepositoryImp;

import java.math.BigDecimal;

import static src.application.input.Input.*;
import static src.application.input.ConsoleColors.printError;
import static src.application.input.ConsoleColors.printMessage;

public class SaveProductUseCase implements ISaveProduct {
    private final ProductRepositoryAdapter repository = new ProductRepositoryImp();

    @Override
    public void execute() {
        try {
            final String name = stringInput("DIGITE O NOME DO PRODUTO");
            final BigDecimal value = bigDecimalInput("DIGITE O VALOR DO PRODUTO");
            final int quantity = integerInput("DIGITE A QUANTIDADE DO PRODUTO");
            final Product product = new Product(name, value, quantity);
            repository.saveProduct(product);
            printMessage("PRODUTO SALVO!");
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
}
