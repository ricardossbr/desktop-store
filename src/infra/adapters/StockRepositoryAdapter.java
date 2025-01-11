package src.infra.adapters;

import src.domain.model.Product;
import src.domain.model.Status;
import src.domain.model.Stock;

import java.util.List;
import java.util.Set;

public interface StockRepositoryAdapter {

    List<Stock> getStocks(Set<Integer> stockIds);

    void makeSale(Stock stock);

    void makeLikelySale(Stock stock);

    void makeARealSale(int stockId);

    default int makeLikelySale(Product product, int quantity) {
        final Stock stock = new Stock(product.getId(), quantity, Status.CAR);
        this.makeLikelySale(stock);
        return stock.getId();
    }

    int getNextId();

    void removeStock(int id);
}
