package src.infra.files;

import src.domain.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDatabaseFile {

    private final File myObj;
    private final static String FILE_NAME = "product_database.txt";
    private final static int fields = new Product().getClass().getDeclaredFields().length;

    public ProductDatabaseFile() {
        this.myObj = new File(FILE_NAME);
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writerFile(String string){
        try {
            PrintWriter myWriter = new PrintWriter(new FileOutputStream(this.myObj, true));
            myWriter.append(string).append("\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public List<Product> readFile(){
        final List<Product> products = new ArrayList<>();
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String element = "";
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    String line[] = new String[fields];
                    line = element.split(",");
                    final Product product = new Product(line[0], line[1], line[2], line[3]);
                    products.add(product);
                }
            }
            reader.close();
            return products;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public int getNextId(){
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String element = "";
            int nextId  = 0;
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    nextId++;
                }
            }
            reader.close();
            return nextId == 0 ? 1 : nextId;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 1;
    }

    public Product getLineById(int id){
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String element = "";
            int nextId  = 0;
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    String line[] = new String[fields];
                    line = element.split(",");
                    if(id == Integer.parseInt(line[0])){
                        reader.close();
                        return new Product(line[0], line[1], line[2], line[3]);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public void deleteLineById(int id){
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String element = "";
            int nextId  = 0;
            while ((element = reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    String line[] = new String[fields];
                    line = element.split(",");
                    if(id == Integer.parseInt(line[0])){
                        String finalElement = element;
                        List<String> out = Files.lines(myObj.toPath())
                                .filter(currentLine -> !currentLine.contains(finalElement))
                                .collect(Collectors.toList());
                        Files.write(myObj.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                    }

                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void editLineById(Product product){
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String element = "";
            while ((element = reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    String line[] = new String[fields];
                    line = element.split(",");
                    if(product.getId() == Integer.parseInt(line[0])){
                        String finalElement = element;
                        List<String> out = Files.lines(myObj.toPath())
                                .filter(currentLine -> currentLine.contains(finalElement))
                                .map(r -> {
                                    String[] split = r.split(",");
                                    split[0] = String.valueOf(product.getId() + ",");
                                    split[1] = product.getName() + ",";
                                    split[2] = String.valueOf(product.getValue()) + ",";
                                    split[3] = String.valueOf(product.getQuantity());
                                    return Arrays.stream(split).flatMap(String::lines).collect(Collectors.joining());
                                })
                                .collect(Collectors.toList());
                        Files.write(myObj.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                    }

                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
