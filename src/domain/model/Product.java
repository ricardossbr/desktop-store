package src.domain.model;

import src.infra.files.ProductDatabaseFile;
import src.infra.adapters.ProductRepositoryAdapter;
import src.infra.repository.ProductRepositoryImp;

import java.math.BigDecimal;

import static src.application.input.ConsoleColors.printError;

public class Product {
        private final int id;
        protected String name;
        protected BigDecimal value;
        protected int quantity;

        public Product(){
                ProductRepositoryAdapter repository = new ProductRepositoryImp();
                this.id = repository.getNextId();
        }

        public Product(String id, String name, String value, String quantity){
                this.id = Integer.parseInt(id);
                this.name = name;
                this.value = new BigDecimal(value);
                this.quantity = Integer.parseInt(quantity);
        }

        public Product(String name, BigDecimal value, int quantity){
                ProductDatabaseFile file = new ProductDatabaseFile();
                this.id = file.getNextId();
                this.name = name;
                this.value = value;
                this.quantity = quantity;
        }

        public void prepareToEdit(String name, BigDecimal value, int quantity){
                this.name = name;
                this.value = value;
                this.quantity = quantity;
        }

        public int getId(){return this.id;}

        public String getName(){return this.name;}

        public BigDecimal getValue(){return this.value;}

        public int getQuantity(){return this.quantity;}

        public boolean debitQuantity(int quantity){
            if(this.quantity >= quantity){
                this.quantity -= quantity;
                return true;
            }else {
                printError("Saldo insuficiente!");
                return false;
            }
        }

        public void creditQuantity(int quantity){
                if(quantity > 0){
                  this.quantity += quantity;
                }else{
                   printError("Valor não pode ser negativo!");
                }
        }

        @Override
        public String toString() {
                return id + "," + name + "," + value + "," + quantity + "\n";
        }

        public String toJson(){
                return " {\n" +
                        "\t\"id\": " + id + ",\n" +
                        "\t\"name\": \"" + name + "\",\n" +
                        "\t\"value\": " + value + ",\n" +
                        "\t\"quantity\": " + quantity + ",\n" +
                        " }";
        }


}
