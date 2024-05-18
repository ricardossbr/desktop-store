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
    public List<Car> getCars() {
        return carRepository.getCar();
    }

    @Override
    public Car getCar() {
        final int value = getInt("DIGITE O ID DO CARRINHO");
        return carRepository.getById(value);
    }

    @Override
    public void createCar() {
        Car car = new Car();
        Product product = getProduct();
        if(product==null) return;
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
        Car car = getCarFromFile();
        Product product = getProduct();
        if(car == null || product==null) return;
        final int quantity = getInt("DIGITE O QUANTIDADE DO PRODUTO");
        product.debitQuantity(quantity);
        car.addProduct(product);
        productService.editProduct(product);
        stockService.makeLikelySale(product, quantity);
        carRepository.save(car);
    }



    @Override
    public void removeProduct() {
        Car carFound = getCarFromFile();
        Product product = getProduct();
        List<Stock> stocks = stockService.getStocks(carFound.getIdentifyStock());
        if(carFound == null || product==null) return;
        carFound.removeProduct(product);
        stocks.stream().filter(stock -> stock.getProduct_id() == product.getId()).forEach(stock -> {
           product.creditQuantity(stock.getQuantity());
        });
        int asInt = stocks.stream().filter(stock -> stock.getProduct_id() == product.getId()).mapToInt(Stock::getId).findFirst().getAsInt();
        productService.editProduct(product);
        stockService.removeStock(carFound.getStockId(asInt));
        carRepository.update(carFound);
    }

    @Override
    public void finishCar() {
        Car car = getCarFromFile();
        if(car == null) return;
        car.getIdentifyStock().forEach(stockService::removeStock);
        this.carRepository.delete(car.getId());
    }

    @Override
    public void finishAndMakeSale() {
        Car car = getCarFromFile();
        if(car == null) return;
        stockService.makeSale();
        
    }

    private Car getCarFromFile() {
        final int value = getInt("DIGITE O ID DO CARRINHO");
        Car carFound = carRepository.getById(value);
        if(carFound == null) {
            System.out.println("Carrinho náo encontrado!");
            return null;
        }
        return carFound;
    }

    private Product getProduct() {
        final int value = getInt("DIGITE O ID DO PRODUTO");
        Optional<Product> productFound = productService.getProduct(value);
        if(productFound.isEmpty()) {
            System.out.println("Produto náo encontrado!");
            return null;
        }
        return productFound.get();
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
