package src.infra.repository;

import src.domain.Product;
import src.domain.Stock;

import java.util.List;

public interface StockRepository {

    void makeSale(Stock stock);

    int getNextId();

}
