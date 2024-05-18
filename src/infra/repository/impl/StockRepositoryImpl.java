package src.infra.repository.impl;

import src.domain.Stock;
import src.infra.files.StockDataBaseFile;
import src.infra.repository.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StockRepositoryImpl implements StockRepository {

    private final StockDataBaseFile dataBaseFile;

    public StockRepositoryImpl(){
        this.dataBaseFile = new StockDataBaseFile();
    }

    @Override
    public List<Stock> getStocks(Set<Integer> stockIds) {
        List <Stock> stocks = new ArrayList<>();
        stockIds.stream().forEach( id -> stocks.add(dataBaseFile.getLineById(id)));
        return stocks;
    }

    @Override
    public void makeSale(Stock stock) {
        this.dataBaseFile.writerFile(stock.toString());
    }

    @Override
    public void makeLikelySale(Stock stock) {
        this.dataBaseFile.writerFile(stock.toString());
    }

    @Override
    public void makeARealSale(int stockId) {
        Stock stock = this.dataBaseFile.getLineById(stockId);
        stock.makeSoldStatus();
        this.dataBaseFile.editLineById(stock);
    }

    @Override
    public int getNextId() {
        return dataBaseFile.getNextId();
    }

    @Override
    public void removeStock(int id) {
        this.dataBaseFile.deleteLineById(id);
    }
}
