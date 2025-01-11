package src.domain.usecase;

import src.domain.adapters.IFinishCar;
import src.domain.model.Car;
import src.domain.model.Product;
import src.domain.model.Stock;
import src.infra.adapters.CarRepositoryAdapter;
import src.infra.adapters.ProductRepositoryAdapter;
import src.infra.adapters.StockRepositoryAdapter;
import src.infra.repository.CarRepositoryImp;
import src.infra.repository.ProductRepositoryImp;
import src.infra.repository.StockRepositoryImpl;

import java.util.List;
import java.util.Optional;

import static src.application.input.ConsoleColors.printError;
import static src.domain.model.Status.CAR;

public class FinishCarUseCase implements IFinishCar {
    private final CarRepositoryAdapter repositoryCar = new CarRepositoryImp();
    private final StockRepositoryAdapter repositoryStock = new StockRepositoryImpl();
    private final ProductRepositoryAdapter repositoryProduct = new ProductRepositoryImp();

    @Override
    public void execute() {
        final Car car = repositoryCar.getCar().orElseGet(Car::new);
        final List<Stock> stocks = repositoryStock.getStocks(car.getIdentifyStock());
        car.getProducts().forEach(product -> {
            stocks.stream()
                    .filter(stock -> stock.getProduct_id() == product.getId())
                    .filter(stock -> stock.getStatus().equals(CAR.name()))
                    .forEach(stock -> {
                        product.creditQuantity(stock.getQuantity());
                        repositoryStock.removeStock(stock.getId());
                    });
            editProduct(product);
        });
        car.cleanCar();
        repositoryCar.update(car);
    }

    private void editProduct(Product product) {
        final Optional<Product> productFound = repositoryProduct.getById(product.getId());
        if(productFound.isPresent()) {
            final Product newProduct = productFound.get();
            newProduct.prepareToEdit(product.getName(), product.getValue(), product.getQuantity());
            repositoryProduct.editProduct(newProduct);
            return;
        }
        printError("id do produto não foi encontrado!");
    }
}
