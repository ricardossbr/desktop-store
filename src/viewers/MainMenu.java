package src.viewers;

import src.viewers.impl.menu.*;
import src.viewers.impl.submenu.AddProductFromCar;
import src.viewers.impl.submenu.SubMenu;

import java.util.Map;
import java.util.Scanner;

import static src.viewers.ConsoleColors.*;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    final SubMenu subMenu = new SubMenu();

    private final Map<Integer, EventMenu> menu =
            Map.of(
                    1, new ListProduct(),
                    2, new SaveProduct(),
                    3, new EditProduct(),
                    4, new DeleteProduct(),
                     5, new SubMenu(),
                    6, new MakeSaleMultiStock()
            );


    public void handleMenu() {
        while(true){
            showMenu();
            final var input = scanner.nextInt();
            if(input == 0){
                break;
            }

            if(!menu.containsKey(input)){
                printError("Invalid menu option");
                continue;
            }

            final var eventMenu = menu.get(input);
            eventMenu.execute();
        }
    }

    private void showMenu() {
        printMenu("-----------------------------------");
        printMenu("1 - Listar produtos");
        printMenu("2 - Cadastrar novo produto");
        printMenu("3 - Editar produto");
        printMenu("4 - Excluir produto");
        printMenu("5 - Carinho de compras");
        printMenu("6 - Vender");
        printMenu("0 - EXIT!");
        printMenu("-----------------------------------");
    }

}
