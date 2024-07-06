package src.domain;

import src.infra.repository.StockRepository;
import src.infra.repository.impl.StockRepositoryImpl;

public class Stock {
    private final int id;
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

    public Stock(String stockId, String product_id, String quantity, String status){
        this.id = Integer.parseInt(stockId);
        this.product_id = Integer.parseInt(product_id);
        this.quantity = Integer.parseInt(quantity);
        this.status = Status.valueOf(status);
    }

    public int getId() {
        return id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status.name();
    }

    public void makeCarStatus(){
        this.status = Status.CAR;
    }

    public void makeSoldStatus(){
        this.status = Status.SOLD;
    }

    @Override
    public String toString() {
        return id + "," + product_id + "," + quantity + "," + status.name() + "\n";
    }
}
