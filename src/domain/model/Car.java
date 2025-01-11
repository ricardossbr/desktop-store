package src.domain.model;

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

    public void setId(String id){
        this.id = Integer.parseInt(id);
    }

    public int getId() {
        return id;
    }

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(this.products);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products = this.products.stream().filter(r -> r.getId() != product.getId()).collect(Collectors.toSet());
    }

    public void cleanCar() {
        this.products = new HashSet<>();
        this.stockIds = new HashSet<>();
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

    @Override
    public String toString() {
        final StringBuilder print = new StringBuilder();
        print.append(id).append(",").append('\n');
        this.products.forEach(r -> print.append("\t").append(r.toString()));
        this.stockIds.forEach(r -> print.append("\t").append(r).append("\n"));
        return print.toString();
    }
}
