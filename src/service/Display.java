package src.service;

import src.domain.Product;
import src.infra.DatabaseImp;

import java.util.List;
import java.util.Scanner;

public class Display {

    private final Scanner scanner = new Scanner(System.in);
    private final DatabaseImp databaseImp = new DatabaseImp();

    private void handleMenu() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                getProduct();
                showMenu();
                break;
            case "2":
                salveProduct();
                showMenu();
                break;
            case "3":
                editProduct();
                showMenu();
                break;
            case "4":
                deleteProduct();
                showMenu();
                break;
            case "exit":
                break;
            default:
                showMenu();
                break;

        }
    }

    public void showMenu() {
        System.out.println("1 - Listar produtos");
        System.out.println("2 - Cadastrar novo produto");
        System.out.println("3 - Editar produto");
        System.out.println("4 - Excluir produto");
        handleMenu();
    }

    private void getProduct() {
        System.out.println("----GET PRODUCT-----");
        List<Product> products = databaseImp.getProducts();
        products.forEach(Product::print);
    }

    private void salveProduct() {

        System.out.println("DIGITE O NOME DO PRODUTO");
        String name = this.scanner.nextLine();

        System.out.println("DIGITE O VALOR DO PRODUTO");
        int value = 0;
        try {
             value = this.scanner.nextInt();
        } catch (Exception e) {
            e.getMessage();
        }

        System.out.println("DIGITE A QUANTIDADE DO PRODUTO");
        int quantity = 0;
        try {
            quantity = this.scanner.nextInt();
        } catch (Exception e) {
            e.getMessage();
        }

        Product product = new Product(name, value, quantity);
        databaseImp.saveProduct(product);

        System.out.println("PRODUTO SALVO!");
        showMenu();

    }


    private void editProduct() {
        System.out.println("----EDIT PRODUCT-----");
        System.out.println("----DIGITE O ID DO PRODUTO!-----");
        int id = scanner.nextInt();
        Product productFound = databaseImp.getById(id);
        if(productFound != null) {
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

            System.out.println("DIGITE O VALOR DO PRODUTO");
            int value = 0;
            try {
                value = this.scanner.nextInt();
            } catch (Exception e) {
                e.getMessage();
            }

            System.out.println("DIGITE A QUANTIDADE DO PRODUTO");
            int quantity = 0;
            try {
                quantity = this.scanner.nextInt();
            } catch (Exception e) {
                e.getMessage();
            }
            productFound.prepareToEdit(name, value, quantity);
            databaseImp.editProduct(productFound);
            System.out.println("Produto editado com sucesso!");
        }
        System.out.println("id do produto não foi encontrado!");
    }

    private void deleteProduct() {
        System.out.println("----DELETE PRODUCT-----");
        int id = scanner.nextInt();
        databaseImp.deleteProduct(id);
    }

}
