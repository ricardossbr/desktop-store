package src.infra.repository;

import src.domain.Product;

import java.util.List;

public interface ProductRepository {

    List<Product> getProducts();

    Product getById(int id);

    void saveProduct(Product product);

    void editProduct(Product product);

    void deleteProduct(int id);


}
