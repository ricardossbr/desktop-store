package src.service.impl;

import src.domain.Car;
import src.domain.Product;
import src.domain.Stock;
import src.infra.repository.CarRepository;
import src.infra.repository.impl.CarRepositoryImp;
import src.service.CarService;
import src.service.ProductService;
import src.service.StockService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static src.domain.Status.CAR;
import static src.viewers.ConsoleColors.printError;
import static src.viewers.ConsoleColors.printMessage;

public class CarServiceImpl implements CarService {

    private final CarRepository carRepository = new CarRepositoryImp();
    private final Scanner scanner = new Scanner(System.in);
    private final ProductService productService = new ProductServiceImpl();
    private final StockService stockService = new StockServiceImpl();


    @Override
    public boolean checkAlreadyCar() {
        Optional<Car> currentCar = carRepository.getCar();
        return currentCar.isPresent();
    }

    @Override
    public void createCar() {
        Car car = getCar();
        Optional<Product> productFound = getProduct();
        if (productFound.isPresent()) {
            Product product = productFound.get();
            final int quantity = getInt("DIGITE O QUANTIDADE DO PRODUTO");
            boolean debitIsOk = product.debitQuantity(quantity);
            if (debitIsOk) {
                productService.editProduct(product);
                car.addProduct(product);
                int stockId = stockService.makeLikelySale(product, quantity);
                car.identifyStock(stockId);
                carRepository.save(car);
            } else {
                createCar();
            }
        } else {
            createCar();
        }
    }

    @Override
    public void addProduct() {
        Car car = getCar();
        Optional<Product> productFound = getProduct();
        if (productFound.isPresent()) {
            Product product = productFound.get();
            final int quantity = getInt("DIGITE O QUANTIDADE DO PRODUTO");
            boolean debitIsOk = product.debitQuantity(quantity);
            if (debitIsOk) {
                car.addProduct(product);
                productService.editProduct(product);
                final int i = stockService.makeLikelySale(product, quantity);
                car.identifyStock(i);
                carRepository.save(car);
            }
        }
    }


    @Override
    public void removeProduct() {
        Car car = getCar();
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
            carRepository.update(car);
        }
    }

    @Override
    public void finishCar() {
        Car car = getCar();
        List<Stock> stocks = stockService.getStocks(car.getIdentifyStock());
//        car.removeProduct(product);
//        stocks.stream()
//                .filter(stock -> stock.getProduct_id() == product.getId())
//                .filter(stock -> stock.getStatus().equals(CAR.name()))
//                .forEach(stock -> {
//                    product.creditQuantity(stock.getQuantity());
//                    car.removeStock(stock.getId());
//                    stockService.removeStock(stock.getId());
//                });
//        productService.editProduct(product);
        car.cleanCar();
        carRepository.update(car);
    }

    @Override
    public void finishAndMakeSale() {
        Car car = getCar();
        List<Stock> stocks = stockService.getStocks(car.getIdentifyStock());
        stockService.makeSaleMultiStocks(stocks);
        car.cleanCar();
        carRepository.update(car);
    }


    private Optional<Product> getProduct() {
        final int value = getInt("DIGITE O ID DO PRODUTO");
        Optional<Product> productFound = productService.getProduct(value);
        if (productFound.isEmpty()) {
            printError("Produto náo encontrado!");
            return Optional.empty();
        }
        return productFound;
    }

    private int getInt(String message) {
        printMessage(message);
        int value = 0;
        try {
            value = this.scanner.nextInt();
        } catch (Exception e) {
            e.getMessage();
        }
        return value;
    }

    private Car getCar() {
        Car car;
        Optional<Car> currentCar = carRepository.getCar();
        car = currentCar.orElseGet(Car::new);
        return car;
    }

}
