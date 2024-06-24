package src.service.impl;

import src.domain.Product;
import src.exception.InvalidInputException;
import src.infra.repository.impl.ProductRepositoryImp;
import src.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static src.input.Input.bigDecimalInput;
import static src.input.Input.integerInput;
import static src.viewers.ConsoleColors.*;

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
        }
        final Product newProduct = productFound.get();
        newProduct.prepareToEdit(product.getName(), product.getValue(), product.getQuantity());
        databaseImp.editProduct(newProduct);
    }

    @Override
    public void deleteProduct(int id) {
        databaseImp.deleteProduct(id);
    }
}
