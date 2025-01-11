package src.infra.repository;

import src.domain.model.Stock;
import src.infra.adapters.StockRepositoryAdapter;
import src.infra.files.StockDataBaseFile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StockRepositoryImpl implements StockRepositoryAdapter {

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
        this.dataBaseFile.editLineById(stock);
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
