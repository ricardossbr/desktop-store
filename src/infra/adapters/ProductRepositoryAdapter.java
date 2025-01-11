package src.infra.adapters;

import src.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryAdapter {

    List<Product> getProducts();

    Optional<Product> getById(int id);

    void saveProduct(Product product);

    void editProduct(Product product);

    void deleteProduct(int id);

    int getNextId();
}
