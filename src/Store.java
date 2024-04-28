package src;

import java.io.File;
import java.util.Scanner;

public class Store {
    public static void main(String[] arg){
        showMenu();
    }

    private static void showMenu() {
        System.out.println("1 - Listar produtos");
        System.out.println("2 - Cadastrar novo produto");
        System.out.println("3 - Editar produto");
        System.out.println("4 - Excluir produto");
        handleMenu();
    }

    private static void handleMenu() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        switch (input){
            case "1":
                getProduct();
                break;
            case "2":
                salveProduct();
                break;
            case "3":
                editProduct();
                break;
            case "4":
                deleteProduct();
                break;
            default:
                
        }
    }

    private static void getProduct() {
        System.out.println("----GET PRODUCT-----");
    }

    private static void salveProduct() {
        Scanner scanner = new Scanner(System.in);
        Product product = new Product();

        System.out.println("DIGITE O NOME DO PRODUTO");
        product.name = scanner.nextLine();

        System.out.println("DIGITE O VALOR DO PRODUTO");
        try {
            product.value = scanner.nextInt();
        }catch (Exception e){
            e.getMessage();
        }


        System.out.println("DIGITE A QUANTIDADE DO PRODUTO");
        try {
            product.quantity = scanner.nextInt();
        }catch (Exception e){
            e.getMessage();
        }

        saveInDataBase(product);

        System.out.println("PRODUTO SALVO!");
        showMenu();

    }



    private static void editProduct() {
        System.out.println("----EDIT PRODUCT-----");
    }

    private static void deleteProduct() {
        System.out.println("----DELETE PRODUCT-----");
    }

    private static void saveInDataBase(Product product) {

    }

    public static class Product {
        protected String name;
        protected int value;
        protected int quantity;
    }


