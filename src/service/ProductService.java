package src.service;

import src.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProduct();

    void salveProduct();

    void editProduct();

    void deleteProduct();
}
