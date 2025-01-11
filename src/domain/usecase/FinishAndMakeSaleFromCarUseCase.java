package src.domain.usecase;

import src.domain.adapters.IFinishAndMakeSaleFromCar;
import src.domain.model.Car;
import src.domain.model.Stock;
import src.infra.adapters.CarRepositoryAdapter;
import src.infra.adapters.StockRepositoryAdapter;
import src.infra.repository.CarRepositoryImp;
import src.infra.repository.StockRepositoryImpl;

import java.util.List;

public class FinishAndMakeSaleFromCarUseCase implements IFinishAndMakeSaleFromCar {
    private final CarRepositoryAdapter repository = new CarRepositoryImp();
    private final StockRepositoryAdapter repositoryStock = new StockRepositoryImpl();


    @Override
    public void execute() {
        final Car car = repository.getCar().orElseGet(Car::new);
        final List<Stock> stocks = repositoryStock.getStocks(car.getIdentifyStock());
        stocks.forEach(stock1 -> {
            stock1.makeSoldStatus();
            repositoryStock.makeSale(stock1);
        });
        car.cleanCar();
        repository.update(car);
    }
}
