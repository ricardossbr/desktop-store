package src.domain;

import src.infra.repository.CarRepository;
import src.infra.repository.impl.CarRepositoryImp;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Car {

    private int id;
    private Set<Product> products;
    private Set<Integer> stockIds;


    public Car(){
        this.id = 1;
        this.products = new HashSet<>();
        this.stockIds = new HashSet<>();
    }

    public Car(int id, Set<Product> products, Set<Integer> stockIds){
        this.id = id;
        this.products = products;
        this.stockIds = stockIds;
    }

    public void setId(String id){
        this.id = Integer.parseInt(id);
    }

    public int getId() {
        return id;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products = this.products.stream().filter(r -> r.getId() != product.getId()).collect(Collectors.toSet());
    }

    public void removeStock(int stockId) {
        this.stockIds.remove(stockId);
    }

    public void identifyStock(int stockId){
        this.stockIds.add(stockId);
    }

    public Set<Integer> getIdentifyStock(){
        return Collections.unmodifiableSet(this.stockIds);
    }

    public int getStockId(int stockId){
        return this.stockIds.contains(stockId) ? stockId : 0;
    }

    @Override
    public String toString() {
        final StringBuilder print = new StringBuilder();
        print.append(id).append(",").append('\n');
        this.products.forEach(r -> print.append("\t").append(r.toString()));
        this.stockIds.forEach(r -> print.append("\t").append(r).append("\n"));
        return print.toString();
    }
}
