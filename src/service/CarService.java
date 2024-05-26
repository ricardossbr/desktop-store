package src.service;

import src.domain.Car;
import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> getCars();

    Optional<Car> getCar();

    void createCar();

    void addProduct();

    void removeProduct();

    void finishCar();

    void finishAndMakeSale();
}
