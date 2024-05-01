package src.service;

import src.domain.Product;
import src.infra.DatabaseImp;
import java.util.List;
import java.util.Scanner;

public class Display {

    private final Scanner scanner = new Scanner(System.in);
    private final DatabaseImp databaseImp = new DatabaseImp();

    private void handleMenu() {
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
        final List<Product> products = databaseImp.getProducts();
        printList(products);

    }

    private void salveProduct() {
        final String name = getName();
        final int value = getInt("DIGITE O VALOR DO PRODUTO");
        final int quantity = getInt("DIGITE A QUANTIDADE DO PRODUTO");
        final Product product = new Product(name, value, quantity);
        databaseImp.saveProduct(product);
        System.out.println("PRODUTO SALVO!");
        showMenu();

    }

    private void editProduct() {
        System.out.println("----EDIT PRODUCT-----");
        final int id = getInt("----DIGITE O ID DO PRODUTO!-----");
        final Product productFound = databaseImp.getById(id);
        if(productFound != null) {
            final String name = getName();
            final int value = getInt("DIGITE O VALOR DO PRODUTO");
            final int quantity = getInt("DIGITE A QUANTIDADE DO PRODUTO");
            productFound.prepareToEdit(name, value, quantity);
            databaseImp.editProduct(productFound);
            System.out.println("Produto editado com sucesso!");
        }
        System.out.println("id do produto não foi encontrado!");
    }

    private void deleteProduct() {
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

    private void printList(List<Product> products) {
        final int column1 = products.stream().map(r -> String.valueOf(r.getId()).length()).max(Integer::compareTo).get();
        final int column2 = products.stream().map(r -> r.getName().length()).max(Integer::compareTo).get();
        final int column3 = products.stream().map(r -> String.valueOf(r.getValue()).length()).max(Integer::compareTo).get();
        final int column4 = products.stream().map(r -> String.valueOf(r.getQuantity()).length()).max(Integer::compareTo).get();

        final StringBuilder printColumn = new StringBuilder();
        printColumn.append(getStringPrint(column1));
        printColumn.append(getStringPrint(column2));
        printColumn.append(getStringPrint(column3));
        printColumn.append(getStringPrint(column4));
        printColumn.append("+");

        products.forEach(r -> {
            System.out.println(printColumn);
            System.out.println("|" + printField(String.valueOf(r.getId()), column1 )+ "|" + printField(r.getName(), column2 ) + "|" + printField(String.valueOf(r.getValue()), column3 ) + "|" + printField(String.valueOf(r.getQuantity()), column4) + "|");
            System.out.println(printColumn);
        });
    }

    private StringBuilder getStringPrint(int colum) {
        colum++;
        StringBuilder print = new StringBuilder();
        for (int i = 0; i <= colum; i++){
            if(i ==0) print.append("+");
            print.append("-");
        }
        return print;
    }

    private String printField(String field, int columnSize) {
        columnSize++;
        StringBuilder result = new StringBuilder();
        result.append(" ");
        result.append(field);
        for (int i = result.length(); i <= columnSize; i++) {
            result.append(" ");
        }
        return result.toString();
    }

}
