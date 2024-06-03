package src.service.impl;

import src.domain.Product;
import src.domain.Status;
import src.domain.Stock;
import src.infra.repository.StockRepository;
import src.infra.repository.impl.StockRepositoryImpl;
import src.service.ProductService;
import src.service.StockService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class StockServiceImpl implements StockService {

    private final StockRepository repository;

    private final ProductService productService;

    private final Scanner scanner = new Scanner(System.in);

    public StockServiceImpl() {
        this.repository = new StockRepositoryImpl();
        this.productService = new ProductServiceImpl();
    }


    @Override
    public List<Stock> getStocks(Set<Integer> ids) {
       return this.repository.getStocks(ids);
    }

    @Override
    public void makeSaleMultiStocks() {
        final int id = getInt("----DIGITE O ID DO PRODUTO!-----");
        Optional<Product> product = productService.getProduct(id);
        if (product.isPresent()) {
            Product foundProduct = product.get();
            final int quantity = getInt("----DIGITE A QUANTIDADE DE PRODUTOS PARA ESSA COMPRA-----");
            Stock stock = new Stock(foundProduct.getId(), quantity, Status.SOLD);
            if(quantity > foundProduct.getQuantity()){
                System.err.println("Produto sem estoque para essa quantidade!");
                return;
            }
            repository.makeSale(stock);
            foundProduct.debitQuantity(quantity);
            productService.editProduct(foundProduct);
        }else {
            System.err.println("Produto não encontrado!");
        }

    }

    @Override
    public void makeSaleMultiStocks(List<Stock> stock) {
        stock.forEach(stock1 -> {
            stock1.makeSoldStatus();
            repository.makeSale(stock1);
        });
    }

    @Override
    public int makeLikelySale(Product product, int quantity) {
        Stock stock = new Stock(product.getId(), quantity, Status.CAR);
        repository.makeLikelySale(stock);
        return stock.getId();
    }

    @Override
    public void removeStock(int stockId) {
        repository.removeStock(stockId);
    }

    @Override
    public void updateStock(int stockId) {

    }


    private int getInt(String message) {
        System.out.println(message);
        int value = 0;
        try {
            value = this.scanner.nextInt();
        } catch (Exception e) {
            e.getMessage();
        }
        return value;
    }

    private void getName() {

    }

}
