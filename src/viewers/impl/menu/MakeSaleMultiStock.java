package src.viewers.impl.menu;

import src.domain.Product;
import src.domain.Status;
import src.domain.Stock;
import src.exception.InvalidInputException;
import src.service.ProductService;
import src.service.StockService;
import src.service.impl.ProductServiceImpl;
import src.service.impl.StockServiceImpl;
import src.viewers.EventMenu;

import java.util.Optional;

import static src.input.Input.integerInput;
import static src.viewers.ConsoleColors.printError;

public class MakeSaleMultiStock implements EventMenu {
    private final StockService service = new StockServiceImpl();
    private final ProductService productService = new ProductServiceImpl();

    @Override
    public void execute() {
        try {
            final int id = integerInput("----DIGITE O ID DO PRODUTO!-----");
            Optional<Product> product = productService.getProduct(id);
            if (product.isPresent()) {
                Product foundProduct = product.get();
                final int quantity = integerInput("----DIGITE A QUANTIDADE DE PRODUTOS PARA ESSA COMPRA-----");
                Stock stock = new Stock(foundProduct.getId(), quantity, Status.SOLD);
                if(quantity > foundProduct.getQuantity()){
                    printError("Produto sem estoque para essa quantidade!");
                    return;
                }
                service.makeStock(stock);
                foundProduct.debitQuantity(quantity);
                productService.editProduct(foundProduct);
            }else {
                printError("Produto não encontrado!");
            }
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }

    }
}
