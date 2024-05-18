package src.infra.files;

import src.domain.Car;
import src.domain.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CarDatabaseFile {

    private final File myObj;
    private final static String FILE_NAME = "car_database.txt";
    private final static String SEPARATOR = ",";

    public CarDatabaseFile() {
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

    public List<Car> readFile(){
        final List<Product> products = new ArrayList<>();
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String element = "";
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    String[] line = element.split(",");
                    final Product product = new Product(line[0], line[1], line[2], line[3]);
                    products.add(product);
                }
            }
            reader.close();
            return Arrays.asList();
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
            return nextId;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 1;
    }

    public Car getLineById(int id){
        try {
            Car car = Files.lines(myObj.toPath())
                    .filter(line -> {
                        String[] split = line.split(SEPARATOR);
                        return split[0].equals(String.valueOf(id));
                    })
                    .map(line -> {
                        String[] split = line.split(SEPARATOR);
                        //return new Car(split[0], split[1], split[2], split[3]);
                        return new Car();
                    }).collect(Collectors.toList()).stream().findFirst().orElseGet(null);
            return car;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public void deleteLineById(int id){
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            List<String> out = Files.lines(myObj.toPath())
                    .filter(line -> {
                        String[] split = line.split(SEPARATOR);
                        return !split[0].equals(String.valueOf(id));
                    })
                    .collect(Collectors.toList());
            Files.write(myObj.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void editLineById(Car car){
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            List<String> out = Files.lines(myObj.toPath())
                    .map(line -> {
                        String[] split = line.split(SEPARATOR);
                        if(split[0].equals(String.valueOf(car.getId()))){
                            split[0] = car.getId() + SEPARATOR;
                            /*split[1] = product.getName() + SEPARATOR;
                            split[2] = product.getValue() + SEPARATOR;
                            split[3] = String.valueOf(product.getQuantity());*/
                            return Arrays.stream(split).reduce((a,b)->a+b).orElse("");
                        }
                        return line;
                    })
                    .collect(Collectors.toList());
            Files.write(myObj.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


}
