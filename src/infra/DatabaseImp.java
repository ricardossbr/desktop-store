package src.infra;

import src.domain.Product;

import java.util.List;

public class DatabaseImp implements DataBase{
    private final DatabaseFile file = new DatabaseFile();

    @Override
    public List<Product> getProducts() {
        return file.readFile();
    }

    @Override
    public Product getById(int id) {
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
}
