package src.domain.usecase;

import src.domain.adapters.IListProduct;
import src.domain.model.Product;
import src.infra.adapters.ProductRepositoryAdapter;
import src.infra.repository.ProductRepositoryImp;

import java.util.List;

import static src.application.input.ConsoleColors.printError;
import static src.application.input.ConsoleColors.printMessage;

public class ListProductsUseCase implements IListProduct {
    private final ProductRepositoryAdapter repository = new ProductRepositoryImp();
    private final String columnId = "ID";
    private final String columnName = "Nome";
    private final String columnPrice = "Valor";
    private final String columnQuantity = "Quantidade";

    public void execute(){

        final List<Product> products = repository.getProducts();
        printList(products);
    }

    private void printProducts(List<Product> products) {

        for(int i = 0; i < products.size(); i++){
            final var columnZero = String.valueOf(products.get(i).getId()).length();
            final var columnOne = products.get(i).getName().length();
            final var columnTwo = products.get(i).getValue().toString().length();
            final var columnTree = String.valueOf(products.get(i).getQuantity()).length();
        }

        for (Product product : products) {

        }
    }





    private void printList(List<Product> products) {
        if(products == null || products.isEmpty()) {
            printError("----Não tem nenhum produto cadastrado ainda!-----");
            return;
        }


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
