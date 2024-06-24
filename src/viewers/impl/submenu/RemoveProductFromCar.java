package src.viewers.impl.submenu;

import src.domain.Car;
import src.domain.Product;
import src.domain.Stock;
import src.exception.InvalidInputException;
import src.service.CarService;
import src.service.ProductService;
import src.service.StockService;
import src.service.impl.CarServiceImpl;
import src.service.impl.ProductServiceImpl;
import src.service.impl.StockServiceImpl;
import src.viewers.EventMenu;

import java.util.List;
import java.util.Optional;

import static src.domain.Status.CAR;
import static src.input.Input.integerInput;
import static src.viewers.ConsoleColors.printError;

public class RemoveProductFromCar implements EventMenu {
    private final CarService service = new CarServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final StockService stockService = new StockServiceImpl();

    @Override
    public void execute() {
        Car car = service.getACar().orElseGet(Car::new);
        Optional<Product> productFound = getProduct();
        if (productFound.isPresent()) {
            Product product = productFound.get();
            List<Stock> stocks = stockService.getStocks(car.getIdentifyStock());
            car.removeProduct(product);
            stocks.stream()
                    .filter(stock -> stock.getProduct_id() == product.getId())
                    .filter(stock -> stock.getStatus().equals(CAR.name()))
                    .forEach(stock -> {
                        product.creditQuantity(stock.getQuantity());
                        car.removeStock(stock.getId());
                        stockService.removeStock(stock.getId());
                    });
            productService.editProduct(product);
            service.update(car);
        }
    }

    private Optional<Product> getProduct() {
        try {
            final int value = integerInput("DIGITE O ID DO PRODUTO");
            Optional<Product> productFound = productService.getProduct(value);
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
