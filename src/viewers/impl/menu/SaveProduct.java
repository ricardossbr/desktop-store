package src.viewers.impl.menu;

import src.domain.Product;
import src.exception.InvalidInputException;
import src.service.ProductService;
import src.service.impl.ProductServiceImpl;
import src.viewers.EventMenu;

import java.math.BigDecimal;

import static src.input.Input.*;
import static src.viewers.ConsoleColors.*;

public class SaveProduct implements EventMenu {
    private final ProductService service = new ProductServiceImpl();

    @Override
    public void execute() {
        try {
            final String name = stringInput("DIGITE O NOME DO PRODUTO");
            final BigDecimal value = bigDecimalInput("DIGITE O VALOR DO PRODUTO");
            final int quantity = integerInput("DIGITE A QUANTIDADE DO PRODUTO");
            final Product product = new Product(name, value, quantity);
            service.salveProduct(product);
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
