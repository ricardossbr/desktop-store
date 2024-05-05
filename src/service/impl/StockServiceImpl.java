package src.service.impl;

import src.domain.Product;
import src.domain.Status;
import src.domain.Stock;
import src.infra.repository.StockRepository;
import src.infra.repository.impl.StockRepositoryImpl;
import src.service.ProductService;
import src.service.StockService;

public class StockServiceImpl implements StockService {

    private final StockRepository repository;

    private final ProductService productService;

    public StockServiceImpl() {
        this.repository = new StockRepositoryImpl();
        this.productService = new ProductServiceImpl();
    }


    @Override
    public void makeSale(Product product, int quantity) {
        Stock stock = new Stock(product.getId(), quantity, Status.SOLD);
        repository.makeSale(stock);
        int availableQuantity = product.updateQuantity(quantity);
        Product newProduct = new Product(String.valueOf(product.getId()), product.getName(),String.valueOf(product.getValue()), String.valueOf(availableQuantity) );
        productService.editProduct();
    }

}
