package src.service;

import src.domain.Product;
import src.infra.DatabaseImp;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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
        //products.stream().forEach(Product::print);
        printList(products);

    }

    private void salveProduct() {

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

    private void printList(List<Product> products) {
        AtomicInteger column1 = new AtomicInteger();
        AtomicInteger column2 = new AtomicInteger();
        AtomicInteger column3 = new AtomicInteger();
        AtomicInteger column4 = new AtomicInteger();
        products.stream().forEach( r -> {
                if(column1.get() == 0){
                    column1.set(String.valueOf(r.getId()).length());
                }else if(String.valueOf(r.getId()).length() > column1.get()){
                    column1.set(String.valueOf(r.getId()).length());
                }
                if(column2.get() == 0){
                    column2.set(r.getName().length());
                }else if(r.getName().length() > column2.get()){
                    column2.set(r.getName().length());
                }
                if(column3.get() == 0){
                    column3.set(String.valueOf(r.getValue()).length());
                }else if(String.valueOf(r.getValue()).length() > column3.get()){
                    column3.set(String.valueOf(r.getValue()).length());
                }
                if(column4.get() == 0){
                    column4.set(String.valueOf(r.getValue()).length());
                }else if(String.valueOf(r.getValue()).length() > column4.get()){
                    column4.set(String.valueOf(r.getValue()).length());
                }
        });
        StringBuilder printColumn1 = getStringPrint(column1.get());
        StringBuilder printColumn2 = getStringPrint(column2.get());
        StringBuilder printColumn3 = getStringPrint(column3.get());
        StringBuilder printColumn4 = getStringPrint(column4.get());
        StringBuilder printColumn = new StringBuilder();
        printColumn.append(printColumn1);
        printColumn.append(printColumn2);
        printColumn.append(printColumn3);
        printColumn.append(printColumn4);
        printColumn.append("+");

        products.stream().forEach(r -> {
            System.out.println(printColumn);
            System.out.println("|" + printField(String.valueOf(r.getId()), column1.get() )+ "|" + printField(r.getName(), column2.get() ) + "|" + printField(String.valueOf(r.getValue()), column3.get() ) + "|" + printField(String.valueOf(r.getQuantity()), column4.get()) + "|");
            System.out.println(printColumn);
        });
    }

    private StringBuilder getStringPrint(int colum) {
        StringBuilder print = new StringBuilder();
        for (int i = 0; i < colum; i++){
            if(i ==0) print.append("+");
            print.append("-");
        }
        return print;
    }

    private String printField(String field, int columnSize) {
        StringBuilder result = new StringBuilder(field);
        for (int i = result.length(); i < columnSize; i++) {
            result.append(" ");
        }
        return result.toString();
    }

}
