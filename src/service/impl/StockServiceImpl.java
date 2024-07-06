package src.service.impl;

import src.domain.Product;
import src.domain.Status;
import src.domain.Stock;
import src.infra.repository.StockRepository;
import src.infra.repository.impl.StockRepositoryImpl;
import src.service.StockService;

import java.util.List;
import java.util.Set;

public class StockServiceImpl implements StockService {

    private final StockRepository repository;

    public StockServiceImpl() {
        this.repository = new StockRepositoryImpl();
    }


    @Override
    public List<Stock> getStocks(Set<Integer> ids) {
       return this.repository.getStocks(ids);
    }

    @Override
    public void makeStock(Stock stock) {
        repository.makeSale(stock);
    }


    @Override
    public void makeSaleMultiStocks(List<Stock> stock) {
        stock.forEach(stock1 -> {
            stock1.makeSoldStatus();
            repository.makeSale(stock1);
        });
    }

    @Override
    public int makeLikelySale(Product product, int quantity) {
        final Stock stock = new Stock(product.getId(), quantity, Status.CAR);
        repository.makeLikelySale(stock);
        return stock.getId();
    }

    @Override
    public void removeStock(int stockId) {
        repository.removeStock(stockId);
    }

}
