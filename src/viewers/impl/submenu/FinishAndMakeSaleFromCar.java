package src.viewers.impl.submenu;

import src.domain.Car;
import src.domain.Stock;
import src.infra.repository.StockRepository;
import src.infra.repository.impl.StockRepositoryImpl;
import src.service.CarService;
import src.service.StockService;
import src.service.impl.CarServiceImpl;
import src.service.impl.StockServiceImpl;
import src.viewers.EventMenu;

import java.util.List;
import java.util.Optional;

public class FinishAndMakeSaleFromCar implements EventMenu {
    private final CarService service = new CarServiceImpl();
    private final StockService stockService = new StockServiceImpl();
    private final StockRepository stockRepository = new StockRepositoryImpl();


    @Override
    public void execute() {
        Car car = service.getACar().orElseGet(Car::new);
        List<Stock> stocks = stockService.getStocks(car.getIdentifyStock());
        stocks.forEach(stock1 -> {
            stock1.makeSoldStatus();
            stockRepository.makeSale(stock1);
        });
        car.cleanCar();
        service.update(car);
    }
}
