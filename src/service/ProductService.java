package src.service;

import src.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> getProduct(int id);

    List<Product> getProducts();

    void salveProduct();

    void editProduct();

    void editProduct(Product product);

    void deleteProduct();
}
