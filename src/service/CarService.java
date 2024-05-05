package src.service;

import src.domain.Car;
import src.domain.Product;

public interface CarService {

    Car getCar(int id);

    void AddProduct(Product product);

    void removeProduct(Product product);

    void finishCar(int id);
}
