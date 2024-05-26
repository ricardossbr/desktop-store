package src.infra.files;

import src.domain.Car;
import src.domain.Product;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        final List<Car> cars = new ArrayList<>();
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
            String element = "";
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    final Car car = new Car();
                    String[] line = element.split(",");
                    if(line.length == 1 ){
                        car.setId(line[0]);
                    } else if (line.length == 4) {
                        final Product product = new Product(line[0].replaceAll("[^0-9]", ""), line[1], line[2], line[3]);
                        car.addProduct(product);
                    }
                    cars.add(car);
                }
            }
            reader.close();
            return cars;
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

    public Optional<Car> getLineById(int id){
        try {
            return Files.lines(myObj.toPath())
                    .map(line -> line.split(SEPARATOR))
                    .filter(line -> Integer.parseInt(line[0]) == id)
                    .map(line -> {
                        final Car newCar = new Car();
                        if (line.length == 1) {
                            newCar.setId(line[0]);
                        } else if (line.length == 4) {
                            final Product product = new Product(line[0].replaceAll("[^0-9]", ""), line[1], line[2], line[3]);
                            newCar.addProduct(product);
                        }
                        return newCar;
                    }).toList().stream().findFirst();
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
