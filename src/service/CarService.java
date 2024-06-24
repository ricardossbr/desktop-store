package src.service;

import src.domain.Car;

import java.util.Optional;

public interface CarService {

    Optional<Car> getACar();

    void save(Car car);

    void update(Car car);
}
