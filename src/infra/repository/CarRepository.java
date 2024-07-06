package src.infra.repository;

import src.domain.Car;

import java.util.Optional;

public interface CarRepository {

    Optional<Car> getCar();

    void save(Car car);

    void update(Car car);

    int getNextId();
}
