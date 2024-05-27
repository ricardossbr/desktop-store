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

public class CarServiceImpl implements CarService {

    private final CarRepository carRepository = new CarRepositoryImp();
    private final Scanner scanner = new Scanner(System.in);
    private final ProductService productService = new ProductServiceImpl();
    private final StockService stockService = new StockServiceImpl();


    @Override
    public void createCar() {
        carRepository.getCar();

        Car car = new Car();
        Optional<Product> productFound = getProduct();
        if(productFound.isEmpty()) createCar();
        Product product = productFound.get();
        final int quantity = getInt("DIGITE O QUANTIDADE DO PRODUTO");
        car.addProduct(product);
        product.debitQuantity(quantity);
        productService.editProduct(product);
        int stockId = stockService.makeLikelySale(product, quantity);
        car.identifyStock(stockId);
        carRepository.save(car);
    }

    @Override
    public void addProduct() {
        Car car = new Car();
        Optional<Product> productFound = getProduct();
        if(productFound.isEmpty()) return;
        Product product = productFound.get();
        final int quantity = getInt("DIGITE O QUANTIDADE DO PRODUTO");
        product.debitQuantity(quantity);
        car.addProduct(product);
        productService.editProduct(product);
        stockService.makeLikelySale(product, quantity);
        carRepository.save(car);
    }



    @Override
    public void removeProduct() {
        Car car = new Car();
        Optional<Product> productFound = getProduct();
        if(productFound.isEmpty()) return;
        Product product = productFound.get();
        List<Stock> stocks = stockService.getStocks(car.getIdentifyStock());
        car.removeProduct(product);
        stocks.stream().filter(stock -> stock.getProduct_id() == product.getId()).forEach(stock -> {
           product.creditQuantity(stock.getQuantity());
        });
        int asInt = stocks.stream().filter(stock -> stock.getProduct_id() == product.getId()).mapToInt(Stock::getId).findFirst().getAsInt();
        productService.editProduct(product);
        stockService.removeStock(car.getStockId(asInt));
        carRepository.update(car);
    }

    @Override
    public void finishCar() {

    }

    @Override
    public void finishAndMakeSale() {


        stockService.makeSale();
        
    }


    private Optional<Product> getProduct() {
        final int value = getInt("DIGITE O ID DO PRODUTO");
        Optional<Product> productFound = productService.getProduct(value);
        if(productFound.isEmpty()) {
            System.out.println("Produto náo encontrado!");
            return Optional.empty();
        }
        return productFound;
    }

    private int getInt(String message) {
        System.out.println(message);
        int value = 0;
        try {
            value = this.scanner.nextInt();
        } catch (Exception e) {
            e.getMessage();
        }
        return value;
    }

}
