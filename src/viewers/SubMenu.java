package src.viewers;

import src.domain.Product;
import src.service.CarService;
import src.service.impl.CarServiceImpl;

import java.util.List;
import java.util.Scanner;

import static src.viewers.ConsoleColors.*;

public class SubMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final CarService service = new CarServiceImpl();


    private void handleMenu() {
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                service.addProduct();
                showMenu();
                break;
            case "2":
                service.removeProduct();
                showMenu();
                break;
            case "3":
                service.finishAndMakeSale();
                break;
            case "4":
                service.finishCar();
                break;
            case "5":
                break;
            default:
                showMenu();
                break;
        }
    }

    public void showMenu() {
        if(service.checkAlreadyCar()){
            printSubMenu("1 - Adicionar produto ao carrinho");
            printSubMenu("2 - Excluir produto");
            printSubMenu("3 - Comprar todos os itens do carinho");
            printSubMenu("4 - Finalizar carrinho");
            printSubMenu("5 - Sair");
        }else{
            service.createCar();
        }
        handleMenu();
    }
}
