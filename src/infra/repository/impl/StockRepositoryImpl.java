package src.infra.repository.impl;

import src.domain.Stock;
import src.infra.files.StockDataBaseFile;
import src.infra.repository.StockRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StockRepositoryImpl implements StockRepository {

    private final StockDataBaseFile dataBaseFile;

    public StockRepositoryImpl(){
        this.dataBaseFile = new StockDataBaseFile();
    }

    @Override
    public List<Stock> getStocks(Set<Integer> stockIds) {
        return stockIds.stream()
                .map(dataBaseFile::getLineById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
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
        Optional<Stock> stock = this.dataBaseFile.getLineById(stockId);
        if(stock.isPresent()){
            Stock stockFound = stock.get();
            stockFound.makeSoldStatus();
            this.dataBaseFile.editLineById(stockFound);
        }
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
