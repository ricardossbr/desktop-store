package src.infra.files;

import src.domain.Stock;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static src.viewers.ConsoleColors.printError;
import static src.viewers.ConsoleColors.printMessage;

public class StockDataBaseFile {
    private final File myObj;
    private final static String FILE_NAME = "stock_database.txt";
    private final static String SEPARATOR = ",";


    public StockDataBaseFile() {
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

    public int getNextId(){
        try(final BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String element = "";
            int nextId  = 0;
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    final String[] split = element.split(SEPARATOR);
                    nextId = Integer.parseInt(split[0]);
                }
            }
            nextId++;
            return nextId;
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
        }
        return 0;
    }

    public Optional<Stock> getLineById(int id){
        try {
            return Files.lines(myObj.toPath())
                    .filter(line -> {
                        String[] split = line.split(SEPARATOR);
                        return split[0].equals(String.valueOf(id));
                    })
                    .map(line -> {
                        String[] split = line.split(SEPARATOR);
                        return new Stock(split[0], split[1], split[2], split[3]);
                    }).toList().stream().findFirst();
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
        }
        return Optional.empty();
    }

    public void deleteLineById(int id){
        try {
            final List<String> out = Files.lines(myObj.toPath())
                    .filter(line -> {
                        final String[] split = line.split(SEPARATOR);
                        return !split[0].equals(String.valueOf(id));
                    })
                    .collect(Collectors.toList());
            Files.write(myObj.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            printError("An error occurred. when try to read file");
        }
    }

    public void editLineById(Stock stock){
        try{
            List<String> out = Files.lines(myObj.toPath())
                    .map(line -> {
                        String[] split = line.split(SEPARATOR);
                        if(split[0].equals(String.valueOf(stock.getId()))){
                            split[0] = stock.getId() + SEPARATOR;
                            split[1] = stock.getProduct_id() + SEPARATOR;
                            split[2] = stock.getQuantity() + SEPARATOR;
                            split[3] = stock.getStatus();
                            return Arrays.stream(split).reduce((a, b)->a+b).orElse("");
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
