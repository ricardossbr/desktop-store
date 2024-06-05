package src.viewers;

import src.domain.Product;
import src.service.ProductService;
import src.service.StockService;
import src.service.impl.ProductServiceImpl;
import src.service.impl.StockServiceImpl;

import java.util.List;
import java.util.Scanner;

import static src.viewers.ConsoleColors.*;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ProductService productService = new ProductServiceImpl();
    private final StockService stockService = new StockServiceImpl();
    final SubMenu subMenu = new SubMenu();

    private void handleMenu(boolean withOutProduct) {
        String input = scanner.nextLine();
        if(withOutProduct) {
            if(input.equals("2")) {
                productService.salveProduct();
                showMenu();
                return;
            }else if(input.equals("exit")) {
                return;
            }
        }
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
                subMenu.showMenu();
                showMenu();
                break;
            case "6":
                stockService.makeSaleMultiStocks();
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
        List<Product> products = productService.getProducts();
        boolean withOutProduct = false;
        if(products.isEmpty()) {
            disableMenu("Listar produtos");
            printMenu("2 - Cadastrar novo produto");
            disableMenu("Editar produto");
            disableMenu("Excluir produto");
            disableMenu("Carinho de compras");
            disableMenu("Vender");
        }else{
            printMenu( "1 - Listar produtos");
            printMenu("2 - Cadastrar novo produto");
            printMenu("3 - Editar produto");
            printMenu("4 - Excluir produto");
            printMenu("5 - Carinho de compras");
            printMenu("6 - Vender");
            withOutProduct = true;
        }
        handleMenu(withOutProduct);
    }

    private void getProduct() {
        List<Product> product = productService.getProducts();
        printList(product);
    }


    private void printList(List<Product> products) {
        if(products == null || products.isEmpty()) {
            printError("----Não tem nenhum produto cadastrado ainda!-----");
            return;
        }
        final String columnId = "ID";
        final String columnName = "Nome";
        final String columnPrice = "Valor";
        final String columnQuantity = "Quantidade";
        final int column1 = products.stream().map(r -> String.valueOf(r.getId()).length()).max(Integer::compareTo).stream().mapToInt(r -> r > columnId.length() ? r : columnId.length()).max().orElse(0);
        final int column2 = products.stream().map(r -> r.getName().length()).max(Integer::compareTo).stream().mapToInt(r -> r > columnName.length() ? r : columnName.length()).max().orElse(0);
        final int column3 = products.stream().map(r -> String.valueOf(r.getValue()).length()).max(Integer::compareTo).stream().mapToInt(r -> r > columnPrice.length() ? r : columnPrice.length()).max().orElse(0);
        final int column4 = products.stream().map(r -> String.valueOf(r.getQuantity()).length()).max(Integer::compareTo).stream().mapToInt(r -> r > columnQuantity.length() ? r : columnQuantity.length()).max().orElse(0);

        final StringBuilder printColumn = new StringBuilder();
        printColumn.append(getStringPrint(column1));
        printColumn.append(getStringPrint(column2));
        printColumn.append(getStringPrint(column3));
        printColumn.append(getStringPrint(column4));
        printColumn.append("+");

        printMessage(printColumn.toString());
        printMessage( "|" + printField(columnId, column1 )+ "|" + printField(columnName, column2 ) + "|" + printField(columnPrice, column3 ) + "|" + printField(columnQuantity, column4) + "|");

        products.forEach(r -> {
            printMessage(printColumn.toString());
            printMessage( "|" + printField(String.valueOf(r.getId()), column1 )+ "|" + printField(r.getName(), column2 ) + "|" + printField(String.valueOf(r.getValue()), column3 ) + "|" + printField(String.valueOf(r.getQuantity()), column4) + "|");
            printMessage(printColumn.toString());
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
