package src.service;

import src.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> getProduct(int id);

    List<Product> getProducts();

    void salveProduct(Product product);

    void editProduct(Product product);

    void deleteProduct(int id);
}
