package src.infra.repository;

import src.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> getProducts();

    Optional<Product> getById(int id);

    void saveProduct(Product product);

    void editProduct(Product product);

    void deleteProduct(int id);

    int getNextId();
}
