package src.infra.files;

import src.domain.Stock;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StockDataBaseFile {
    private final File myObj;
    private final static String FILE_NAME = "stock_database.txt";
    private final static String SEPARATOR = ",";


    public StockDataBaseFile() {
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

    public int getNextId(){
        try {
            final BufferedReader reader = openFile();
            String element = "";
            int nextId  = 0;
            while ((element=reader.readLine()) != null){
                if(!element.trim().isEmpty()){
                    nextId++;
                }
            }
            this.closeFile(reader);
            return nextId == 0 ? 1 : nextId;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return 1;
    }

    public Stock getLineById(int id){
        try {
            Stock stock = Files.lines(myObj.toPath())
                    .filter(line -> {
                        String[] split = line.split(SEPARATOR);
                        return split[0].equals(String.valueOf(id));
                    })
                    .map(line -> {
                        String[] split = line.split(SEPARATOR);
                        return new Stock(split[0], split[1], split[2], split[3]);
                    }).collect(Collectors.toList()).stream().findFirst().get();
            return stock;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    public void deleteLineById(int id){
        try {
            final BufferedReader reader = openFile();
            List<String> out = Files.lines(myObj.toPath())
                    .filter(line -> {
                        String[] split = line.split(SEPARATOR);
                        return !split[0].equals(String.valueOf(id));
                    })
                    .collect(Collectors.toList());
            Files.write(myObj.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
            closeFile(reader);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void editLineById(Stock stock){
        try {
            final BufferedReader reader = this.openFile();
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
            this.closeFile(reader);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void closeFile(BufferedReader reader) throws IOException {
        reader.close();
    }

    private BufferedReader openFile() throws FileNotFoundException {
        return new BufferedReader(new FileReader(FILE_NAME));
    }


}
