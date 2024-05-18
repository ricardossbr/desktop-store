package src.service;

import src.domain.Car;
import java.util.List;

public interface CarService {

    List<Car> getCars();

    Car getCar();

    void createCar();

    void addProduct();

    void removeProduct();

    void finishCar();

    void finishAndMakeSale();
}
