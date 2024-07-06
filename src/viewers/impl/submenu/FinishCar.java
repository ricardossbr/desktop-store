package src.viewers.impl.submenu;

import src.domain.Car;
import src.domain.Product;
import src.domain.Stock;
import src.infra.repository.CarRepository;
import src.infra.repository.impl.CarRepositoryImp;
import src.infra.repository.impl.ProductRepositoryImp;
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
import static src.viewers.ConsoleColors.printError;

public class FinishCar implements EventMenu {

    private final CarService carRepository = new CarServiceImpl();
    private final StockService stockService = new StockServiceImpl();
    private final ProductService productRepository = new ProductServiceImpl();

    @Override
    public void execute() {
        final Car car = carRepository.getACar().orElseGet(Car::new);
        final List<Stock> stocks = stockService.getStocks(car.getIdentifyStock());
        car.getProducts().forEach(product -> {
            stocks.stream()
                    .filter(stock -> stock.getProduct_id() == product.getId())
                    .filter(stock -> stock.getStatus().equals(CAR.name()))
                    .forEach(stock -> {
                        product.creditQuantity(stock.getQuantity());
                        stockService.removeStock(stock.getId());
                    });
            editProduct(product);
        });
        car.cleanCar();
        carRepository.update(car);
    }

    private void editProduct(Product product) {
        final Optional<Product> productFound = productRepository.getProduct(product.getId());
        if(productFound.isPresent()) {
            final Product newProduct = productFound.get();
            newProduct.prepareToEdit(product.getName(), product.getValue(), product.getQuantity());
            productRepository.editProduct(newProduct);
            return;
        }
        printError("id do produto não foi encontrado!");
    }
}
