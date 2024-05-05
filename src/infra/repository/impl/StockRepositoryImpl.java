package src.infra.repository.impl;

import src.domain.Product;
import src.domain.Stock;
import src.infra.files.StockDataBaseFile;
import src.infra.repository.StockRepository;
import src.service.StockService;

public class StockRepositoryImpl implements StockRepository {

    private final StockDataBaseFile dataBaseFile;

    public StockRepositoryImpl(){
        this.dataBaseFile = new StockDataBaseFile();
    }

    @Override
    public void makeSale(Stock stock) {
        this.dataBaseFile.writerFile(stock.toString());
    }

    @Override
    public int getNextId() {
        return dataBaseFile.getNextId();
    }
}
