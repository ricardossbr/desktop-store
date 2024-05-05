package src.domain;

import src.infra.repository.StockRepository;
import src.infra.repository.impl.StockRepositoryImpl;

public class Stock {
    private int id;
    private int product_id;
    private int quantity;
    private Status status;

    public Stock(){
        StockRepository repository = new StockRepositoryImpl();
        this.id = repository.getNextId();
    }

    public Stock(int product_id, int quantity, Status status){
        StockRepository repository = new StockRepositoryImpl();
        this.id = repository.getNextId();
        this.product_id = product_id;
        this.quantity = quantity;
        this.status = status;
    }


    @Override
    public String toString() {
        return id + "," + product_id + "," + quantity + "," + status.name() + "\n";
    }
}
