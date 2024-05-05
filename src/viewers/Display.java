package src.viewers;

import src.domain.Product;
import src.service.ProductService;
import src.service.impl.ProductServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Display {

    private final Scanner scanner = new Scanner(System.in);
    private final ProductService productService = new ProductServiceImpl();

    private void handleMenu() {
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                getProduct();
                showMenu();
                break;
            case "2":
                productService.salveProduct();
                showMenu();
                break;
            case "3":
                productService.editProduct();
                showMenu();
                break;
            case "4":
                productService.deleteProduct();
                showMenu();
                break;
            case "5":
                productService.deleteProduct();
                showMenu();
                break;
            case "6":
                productService.deleteProduct();
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
        System.out.println("5 - Carinho de compras");
        System.out.println("6 - Vender");
        handleMenu();
    }

    private void getProduct() {
        System.out.println("----GET PRODUCT-----");
        List<Product> product = productService.getProduct();
        printList(product);
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
