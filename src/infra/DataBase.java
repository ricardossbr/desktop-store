package src.infra;

import src.Product;

import java.util.List;

public interface DataBase {

    List<Product> getProducts();

    Product getById(int id);

    void saveProduct(Product product);

    void editProduct(Product product);

    void deleteProduct(int id);


}
