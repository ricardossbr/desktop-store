package src.domain.usecase;

import src.domain.adapters.IAddProductFromCar;
import src.domain.model.Car;
import src.domain.model.Product;
import src.exception.InvalidInputException;
import src.infra.adapters.CarRepositoryAdapter;
import src.infra.adapters.ProductRepositoryAdapter;
import src.infra.adapters.StockRepositoryAdapter;
import src.infra.repository.CarRepositoryImp;
import src.infra.repository.ProductRepositoryImp;
import src.infra.repository.StockRepositoryImpl;

import java.util.Optional;

import static src.application.input.Input.integerInput;
import static src.application.input.ConsoleColors.printError;

public class AddProductFromCarUseCase implements IAddProductFromCar {
    private final CarRepositoryAdapter repositoryCar = new CarRepositoryImp();
    private final ProductRepositoryAdapter repositoryProduct = new ProductRepositoryImp();
    private final StockRepositoryAdapter stockService = new StockRepositoryImpl();

    @Override
    public void execute() {
        Car car = repositoryCar.getCar().orElseGet(Car::new);
        try {
            int carId = integerInput("DIGITE O ID DO PRODUTO");
            Optional<Product> productFound = repositoryProduct.getById(carId);
            if (productFound.isPresent()) {
                Product product = productFound.get();
                final int quantity = integerInput("DIGITE O QUANTIDADE DO PRODUTO");
                boolean debitIsOk = product.debitQuantity(quantity);
                if (debitIsOk) {
                    repositoryProduct.editProduct(product);
                    car.addProduct(product);
                    final int stockId = stockService.makeLikelySale(product, quantity);
                    car.identifyStock(stockId);
                    repositoryCar.save(car);
                } else {
                    printError("Quantidade para compra e maior que o estoque.");
                }
            }
        } catch (InvalidInputException e) {
            //todo: verify, because, If the error occurs when trying again, the error appears again.
            printError("o id do produto deve ser um numero");
        }
    }
}
