package src.infra.repository.impl;

import src.domain.Product;
import src.infra.files.ProductDatabaseFile;
import src.infra.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryImp implements ProductRepository {
    private final ProductDatabaseFile file = new ProductDatabaseFile();

    @Override
    public List<Product> getProducts() {
        return file.readFile();
    }

    @Override
    public Optional<Product> getById(int id) {
        return file.getLineById(id);
    }

    @Override
    public void saveProduct(Product product) {
        file.writerFile(product.toString());
    }

    @Override
    public void editProduct(Product product) {
        this.file.editLineById(product);
    }

    @Override
    public void deleteProduct(int id) {
        file.deleteLineById(id);
    }

    @Override
    public int getNextId() {
        return file.getNextId();
    }
}
