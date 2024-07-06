package src.infra.files;

import src.domain.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static src.viewers.ConsoleColors.printError;
import static src.viewers.ConsoleColors.printMessage;

public class ProductDatabaseFile {

    private final File myObj;
    private final static String FILE_NAME = "product_database.txt";
    private final static String SEPARATOR = ",";

    public ProductDatabaseFile() {
        this.myObj = new File(FILE_NAME);
        try {
            if (myObj.createNewFile()) {
                printMessage("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writerFile(String string){
        try(PrintWriter myWriter = new PrintWriter(new FileOutputStream(this.myObj, true))) {
            myWriter.append("\n").append(string).append("\n");
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
        }
    }

    public List<Product> readFile(){
        final List<Product> products = new ArrayList<>();
        try(final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String element = "";
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    String[] line = element.split(",");
                    final Product product = new Product(line[0], line[1], line[2], line[3]);
                    products.add(product);
                }
            }
            return products;
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
        }
        return List.of();
    }

    public int getNextId(){
        try(final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String element = "";
            int nextId  = 0;
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    nextId++;
                }
            }
            return nextId;
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
        }
        return 1;
    }

    public Optional<Product> getLineById(int id){
        try {
            return Files.lines(myObj.toPath())
                    .filter(line -> {
                        String[] split = line.split(SEPARATOR);
                        return split[0].equals(String.valueOf(id));
                    })
                    .map(line -> {
                        String[] split = line.split(SEPARATOR);
                        return new Product(split[0], split[1], split[2], split[3]);
                    }).toList().stream().findFirst();

        } catch (IOException e) {
            printError("An error occurred. when try to read file");
        }
        return Optional.empty();
    }

    public void deleteLineById(int id){
        try {
            List<String> out = Files.lines(myObj.toPath())
                    .filter(line -> {
                        String[] split = line.split(SEPARATOR);
                        return !split[0].equals(String.valueOf(id));
                    })
                    .collect(Collectors.toList());
            Files.write(myObj.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
        }
    }

    public void editLineById(Product product){
        try {
            List<String> out = Files.lines(myObj.toPath())
                    .map(line -> {
                        String[] split = line.split(SEPARATOR);
                        if(split[0].equals(String.valueOf(product.getId()))){
                            split[0] = product.getId() + SEPARATOR;
                            split[1] = product.getName() + SEPARATOR;
                            split[2] = product.getValue() + SEPARATOR;
                            split[3] = String.valueOf(product.getQuantity());
                            return Arrays.stream(split).reduce((a,b)->a+b).orElse("");
                        }
                        return line;
                    })
                    .collect(Collectors.toList());
            Files.write(myObj.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
        }
    }

}
