package src.infra.files;

import src.domain.Car;
import src.domain.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static src.viewers.ConsoleColors.printError;

public class CarDatabaseFile {

    private final File myObj;
    private final static String FILE_NAME = "car_database.txt";
    private final static String SEPARATOR = ",";
    private final static int fields = Car.class.getDeclaredFields().length;

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

    public Optional<Car> readFile(){
        try (final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            if(reader.readLine() == null){
                return Optional.empty();
            }
            String element = "";
            final Car car = new Car();
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    String line[] = new String[fields];
                    line = element.split(SEPARATOR);
                    if(line.length == 1 ){
                        if(line[0].contains("\t")){
                            line[0] = line[0].replaceAll("[^0-9]", "");
                            car.identifyStock(Integer.parseInt(line[0]));
                        }else{
                            car.setId(line[0]);
                        }
                    } else if (line.length == 4) {
                        final Product product = new Product(line[0].replaceAll("[^0-9]", ""), line[1], line[2], line[3]);
                        car.addProduct(product);
                    }
                }
            }
            return Optional.of(car);
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public int getNextId(){
        try (final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            String element = "";
            int nextId  = 0;
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    nextId++;
                }
            }
            return nextId;
        } catch (IOException e) {
            printError("An error occurred. when try to get a new id on file");
            e.printStackTrace();
        }
        return 1;
    }

    public void editLineById(Car car){
        try{
            List<String> out = Collections.singletonList(car.toString());
            Files.write(myObj.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
            e.printStackTrace();
        }
    }

}
