package src.viewers.impl.submenu;

import src.domain.Car;
import src.domain.Product;
import src.exception.InvalidInputException;
import src.service.CarService;
import src.service.ProductService;
import src.service.StockService;
import src.service.impl.CarServiceImpl;
import src.service.impl.ProductServiceImpl;
import src.service.impl.StockServiceImpl;
import src.viewers.EventMenu;

import java.util.Optional;

import static src.input.Input.integerInput;
import static src.viewers.ConsoleColors.printError;

public class AddProductFromCar implements EventMenu {

    private final CarService service = new CarServiceImpl();
    private final ProductService productService = new ProductServiceImpl();
    private final StockService stockService = new StockServiceImpl();

    @Override
    public void execute() {
        Car car = service.getACar().orElseGet(Car::new);
        try {
            int carId = integerInput("DIGITE O ID DO PRODUTO");
            Optional<Product> productFound = productService.getProduct(carId);
            if (productFound.isPresent()) {
                Product product = productFound.get();
                final int quantity = integerInput("DIGITE O QUANTIDADE DO PRODUTO");
                boolean debitIsOk = product.debitQuantity(quantity);
                if (debitIsOk) {
                    productService.editProduct(product);
                    car.addProduct(product);
                    int stockId = stockService.makeLikelySale(product, quantity);
                    car.identifyStock(stockId);
                    service.save(car);
                } else {
                    execute();
                }
            }
        } catch (InvalidInputException e) {
            //todo: verify, because, If the error occurs when trying again, the error appears again.
            printError("o id do produto deve ser um numero");
        }
    }

}
