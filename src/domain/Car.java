package src.domain;

import src.infra.repository.CarRepository;
import src.infra.repository.impl.CarRepositoryImp;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Car {

    private int id;
    private Set<Product> products;
    private Set<Integer> stockIds;

    public Car(){
        final CarRepository carRepository = new CarRepositoryImp();
        this.id = carRepository.getNextId();
        this.products = new HashSet<>();
        this.stockIds = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products = this.products.stream().filter(r -> r.getId() != product.getId()).collect(Collectors.toSet());
    }

    public int quantityTotal(){
        return this.products.stream().map(Product::getQuantity).reduce(Integer::sum).orElse(0);
    }

    public void identifyStock(int stockId){
        this.stockIds.add(stockId);
    }

    public Set<Integer> getIdentifyStock(){
        return this.stockIds;
    }

    public int getStockId(int stockId){
        return this.stockIds.contains(stockId) ? stockId : 0;
    }

    @Override
    public String toString() {
        final StringBuilder print = new StringBuilder();
        print.append(id).append(",").append('\n');
        this.products.forEach(r -> {
            print.append("\t").append(r.toString()).append('\n');

        });

        return print.toString();
    }
}
