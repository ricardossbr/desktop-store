package src.viewers.impl.menu;

import src.domain.Product;
import src.exception.InvalidInputException;
import src.infra.repository.impl.ProductRepositoryImp;
import src.service.ProductService;
import src.service.impl.ProductServiceImpl;
import src.viewers.EventMenu;

import java.math.BigDecimal;
import java.util.Optional;

import static src.input.Input.*;
import static src.viewers.ConsoleColors.*;

public class EditProduct implements EventMenu {

    @Override
    public void execute() {
        final ProductService service = new ProductServiceImpl();
        try {
            final int id = integerInput("----DIGITE O ID DO PRODUTO!-----");
            final Optional<Product> productFound = service.getProduct(id);
            if(productFound.isPresent()) {
                final Product product = productFound.get();
                final String name = stringInput("DIGITE O NOME DO PRODUTO");
                final BigDecimal value = bigDecimalInput("DIGITE O VALOR DO PRODUTO");
                final int quantity = integerInput("DIGITE A QUANTIDADE DO PRODUTO");
                if(checkUserConfirmed("Tem certeza que deseja editar o produto?")){
                    product.prepareToEdit(name, value, quantity);
                    service.editProduct(product);
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
