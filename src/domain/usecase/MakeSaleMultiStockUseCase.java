package src.domain.usecase;

import src.domain.adapters.IMakeSaleMultiStock;
import src.domain.model.Product;
import src.domain.model.Status;
import src.domain.model.Stock;
import src.exception.InvalidInputException;
import src.infra.adapters.ProductRepositoryAdapter;
import src.infra.adapters.StockRepositoryAdapter;
import src.infra.repository.ProductRepositoryImp;
import src.infra.repository.StockRepositoryImpl;

import java.util.Optional;

import static src.application.input.Input.integerInput;
import static src.application.input.ConsoleColors.printError;

public class MakeSaleMultiStockUseCase implements IMakeSaleMultiStock {

    private final StockRepositoryAdapter repositoryStock = new StockRepositoryImpl();
    private final ProductRepositoryAdapter repository = new ProductRepositoryImp();

    @Override
    public void execute() {
        try {
            final int id = integerInput("----DIGITE O ID DO PRODUTO!-----");
            final Optional<Product> product = repository.getById(id);
            if (product.isEmpty()) {
                printError("Produto não encontrado!");
                return;
            }
            final Product foundProduct = product.get();
            final int quantity = integerInput("----DIGITE A QUANTIDADE DE PRODUTOS PARA ESSA COMPRA-----");
            if(quantity > foundProduct.getQuantity()){
                printError("Produto sem estoque para essa quantidade!");
                return;
            }
            final Stock stock = new Stock(foundProduct.getId(), quantity, Status.SOLD);
            repositoryStock.makeSale(stock);
            foundProduct.debitQuantity(quantity);
            repository.editProduct(foundProduct);
        } catch (InvalidInputException e) {
            throw new RuntimeException(e);
        }
    }
}
