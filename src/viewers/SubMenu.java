package src.viewers;

import src.domain.Product;
import src.service.CarService;
import src.service.impl.CarServiceImpl;

import java.util.List;
import java.util.Scanner;

public class SubMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final CarService service = new CarServiceImpl();


    private void handleMenu() {
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                service.getCars();
                showMenu();
                break;
            case "2":
                service.createCar();
                showMenu();
                break;
            case "3":
                service.addProduct();
                showMenu();
                break;
            case "4":
                service.removeProduct();
                showMenu();
                break;
            case "5":
                service.finishCar();
                showMenu();
                break;
            case "6":
                service.finishAndMakeSale();
                showMenu();
                break;
            case "7":
                break;
            default:
                showMenu();
                break;
        }
    }

    public void showMenu() {
        System.out.println("1 - Listar os carrinhos");
        System.out.println("2 - Criar carrinho");
        System.out.println("3 - Adicionar carrinho");
        System.out.println("4 - Excluir produto");
        System.out.println("5 - Finalizar carrinho");
        System.out.println("6 - Finalizar carrinho e efetuar comprar");
        System.out.println("7 - Sair");
        handleMenu();
    }


    private void printList(List<Product> products) {
        if(products == null || products.isEmpty()) {
            System.out.println("----Não tem nenhum produto cadastrado ainda!-----");
            return;
        }
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
