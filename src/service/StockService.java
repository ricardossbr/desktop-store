package src.service;

import src.domain.Product;
import src.domain.Stock;

import java.util.List;
import java.util.Set;

public interface StockService {

    List<Stock> getStocks(Set<Integer> ids);

    void makeStock(Stock stock);

    void makeSaleMultiStocks(List<Stock> stock);

    int makeLikelySale(Product product, int quantity);

    void removeStock(int stockId);

}
