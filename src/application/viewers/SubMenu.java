package src.application.viewers;

import src.application.viewers.impl.submenu.AddProductFromCar;
import src.application.viewers.impl.submenu.FinishAndMakeSaleFromCar;
import src.application.viewers.impl.submenu.FinishCar;
import src.application.viewers.impl.submenu.RemoveProductFromCar;

import java.util.Map;
import java.util.Scanner;

import static src.application.input.ConsoleColors.printError;
import static src.application.input.ConsoleColors.printSubMenu;

public class SubMenu implements EventMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final Map<Integer, EventMenu> menu =
            Map.of(
                    1, new AddProductFromCar(),
                    2, new RemoveProductFromCar(),
                    3, new FinishAndMakeSaleFromCar(),
                    4, new FinishCar()
            );

    private void handleMenu() {
        while(true){
            showMenu();
            final var input = scanner.nextInt();
            if(input == 5){
                break;
            }
            if(!menu.containsKey(input)){
                printError("Invalid menu option");
                continue;
            }
            final var eventMenu = menu.get(input);
            eventMenu.execute();
            if(input == 3 || input == 4){
                break;
            }
        }
    }

    public void showMenu() {
        printSubMenu("1 - Adicionar produto ao carrinho");
        printSubMenu("2 - Excluir produto");
        printSubMenu("3 - Comprar todos os itens do carinho");
        printSubMenu("4 - Finalizar carrinho");
        printSubMenu("5 - Sair");
    }

    @Override
    public void execute() {
        handleMenu();
    }
}
