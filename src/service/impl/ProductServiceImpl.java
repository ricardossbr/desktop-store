package src.service.impl;

import src.domain.Product;
import src.infra.repository.impl.ProductRepositoryImp;
import src.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImp databaseImp = new ProductRepositoryImp();
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Optional<Product> getProduct(int id) {
        return Optional.ofNullable(databaseImp.getById(id));
    }

    @Override
    public List<Product> getProducts() {
        final List<Product> products = databaseImp.getProducts();
        return products;
    }

    @Override
    public void salveProduct() {
        final String name = getName();
        final int value = getInt("DIGITE O VALOR DO PRODUTO");
        final int quantity = getInt("DIGITE A QUANTIDADE DO PRODUTO");
        final Product product = new Product(name, value, quantity);
        databaseImp.saveProduct(product);
        System.out.println("PRODUTO SALVO!");
    }

    @Override
    public void editProduct() {
        final int id = getInt("----DIGITE O ID DO PRODUTO!-----");
        final Product productFound = databaseImp.getById(id);
        if(productFound != null) {
            final String name = getName();
            final int value = getInt("DIGITE O VALOR DO PRODUTO");
            final int quantity = getInt("DIGITE A QUANTIDADE DO PRODUTO");
            productFound.prepareToEdit(name, value, quantity);
            databaseImp.editProduct(productFound);
            System.out.println("Produto editado com sucesso!");
        }else{
            System.out.println("id do produto não foi encontrado!");
        }

    }

    @Override
    public void editProduct(Product product) {
        final Product productFound = databaseImp.getById(product.getId());
        if(productFound != null) {
            productFound.prepareToEdit(product.getName(), product.getValue(), product.getQuantity());
            databaseImp.editProduct(productFound);
        }else{
            System.out.println("id do produto não foi encontrado!");
        }
    }

    @Override
    public void deleteProduct() {
        System.out.println("----DELETE PRODUCT-----");
        final int id = scanner.nextInt();
        databaseImp.deleteProduct(id);
    }

    private int getInt(String message) {
        System.out.println(message);
        int value = 0;
        try {
            value = this.scanner.nextInt();
        } catch (Exception e) {
            e.getMessage();
        }
        return value;
    }

    private String getName() {
        System.out.println("DIGITE O NOME DO PRODUTO");
        String name = null;
        while (true){
            name = this.scanner.nextLine();
            if(name == null || name.isEmpty()){
                name = this.scanner.nextLine();
            }else{
                break;
            }
        }
        return name;
    }
}
