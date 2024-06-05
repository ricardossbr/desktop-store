package src.infra.repository;

import src.domain.Stock;

import java.util.List;
import java.util.Set;

public interface StockRepository {

    List<Stock> getStocks(Set<Integer> stockIds);

    void makeSale(Stock stock);

    void makeLikelySale(Stock stock);

    void makeARealSale(int stockId);

    int getNextId();

    void removeStock(int id);
}
