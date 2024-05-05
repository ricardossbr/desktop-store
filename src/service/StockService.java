package src.service;

import src.domain.Product;
import src.domain.Stock;

public interface StockService {

    void makeSale(Product product, int quantity);

}
