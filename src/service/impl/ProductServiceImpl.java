package src.service.impl;

import src.domain.Product;
import src.infra.repository.impl.ProductRepositoryImp;
import src.service.ProductService;

import java.util.List;
import java.util.Optional;

import static src.viewers.ConsoleColors.printError;

public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImp databaseImp = new ProductRepositoryImp();

    @Override
    public Optional<Product> getProduct(int id) {
        return databaseImp.getById(id);
    }

    @Override
    public List<Product> getProducts() {
        return databaseImp.getProducts();
    }

    @Override
    public void salveProduct(Product product) {
        databaseImp.saveProduct(product);
    }

    @Override
    public void editProduct(Product product) {
        final Optional<Product> productFound = databaseImp.getById(product.getId());
        if(productFound.isEmpty()) {
            printError("id do produto não foi encontrado!");
            return;
        }
        final Product newProduct = productFound.orElseGet(Product::new);
        newProduct.prepareToEdit(product.getName(), product.getValue(), product.getQuantity());
        databaseImp.editProduct(newProduct);
    }

    @Override
    public void deleteProduct(int id) {
        databaseImp.deleteProduct(id);
    }
}
