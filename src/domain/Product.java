package src.domain;

import src.infra.files.ProductDatabaseFile;

import java.math.BigDecimal;

public class Product {
        private final int id;
        protected String name;
        protected BigDecimal value;
        protected int quantity;

        public Product(){
                ProductDatabaseFile file = new ProductDatabaseFile();
                this.id = file.getNextId();
        }
        public Product(String id, String name, String value, String quantity){
                this.id = Integer.parseInt(id);
                this.name = name;
                this.value = new BigDecimal(value);
                this.quantity = Integer.parseInt(quantity);
        }

        public Product(String name, int value, int quantity){
                ProductDatabaseFile file = new ProductDatabaseFile();
                this.id = file.getNextId();
                this.name = name;
                this.value = new BigDecimal(value);
                this.quantity = quantity;
        }

        public void prepareToEdit(String name, int value, int quantity){
                this.name = name;
                this.value = new BigDecimal(value);
                this.quantity = quantity;
        }

        public int getId(){return this.id;}

        public String getName(){return this.name;}

        public int getValue(){return this.value.intValue();}

        public int getQuantity(){return this.quantity;}


        @Override
        public String toString() {
                return id + "," + name + "," + value + "," + quantity + "\n";
        }

        public void print(){
                StringBuilder print = new StringBuilder();
                print.append(getStringPrint(String.valueOf(id).length()));
                print.append(getStringPrint(String.valueOf(name).length()));
                print.append(getStringPrint(String.valueOf(value).length()));
                print.append(getStringPrint(String.valueOf(quantity).length()));
                print.append("+");
                System.out.println(print);
                System.out.println("|" + id + "|" + name + "|" + value + "|" + quantity + "|");
                System.out.println(print);
        }

        private StringBuilder getStringPrint(int colum) {
                StringBuilder print = new StringBuilder();
                for (int i = 0; i < colum; i++){
                        if(i ==0) print.append("+");
                        print.append("-");
                }
                return print;
        }
}
