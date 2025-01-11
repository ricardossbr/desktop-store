package src.domain.usecase;

import src.domain.adapters.IRemoveProductFromCar;
import src.domain.model.Car;
import src.domain.model.Product;
import src.domain.model.Stock;
import src.exception.InvalidInputException;
import src.infra.adapters.CarRepositoryAdapter;
import src.infra.adapters.ProductRepositoryAdapter;
import src.infra.adapters.StockRepositoryAdapter;
import src.infra.repository.CarRepositoryImp;
import src.infra.repository.ProductRepositoryImp;
import src.infra.repository.StockRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static src.application.input.Input.integerInput;
import static src.application.input.ConsoleColors.printError;
import static src.domain.model.Status.CAR;

public class RemoveProductFromCarUseCase implements IRemoveProductFromCar {
    private final CarRepositoryAdapter repositoryCar = new CarRepositoryImp();
    private final ProductRepositoryAdapter repositoryProduct = new ProductRepositoryImp();
    private final StockRepositoryAdapter repositoryStock = new StockRepositoryImpl();
    @Override
    public void execute() {
        final Car car = repositoryCar.getCar().orElseGet(Car::new);
        final Optional<Product> productFound = getProduct();
        if (productFound.isPresent()) {
            final Product product = productFound.get();
            final List<Stock> stocks = repositoryStock.getStocks(car.getIdentifyStock());
            car.removeProduct(product);
            stocks.stream()
                    .filter(stock -> stock.getProduct_id() == product.getId())
                    .filter(stock -> stock.getStatus().equals(CAR.name()))
                    .forEach(stock -> {
                        product.creditQuantity(stock.getQuantity());
                        car.removeStock(stock.getId());
                        repositoryStock.removeStock(stock.getId());
                    });
            repositoryProduct.editProduct(product);
            repositoryCar.update(car);
        }
    }

    private Optional<Product> getProduct() {
        try {
            final int value = integerInput("DIGITE O ID DO PRODUTO");
            final Optional<Product> productFound = repositoryProduct.getById(value);
            if (productFound.isEmpty()) {
                printError("Produto náo encontrado!");
                return Optional.empty();
            }
            return productFound;
        } catch (InvalidInputException e) {
            printError("Id do produto deve ser um numero inteiro");
            return Optional.empty();
        }
    }
}
