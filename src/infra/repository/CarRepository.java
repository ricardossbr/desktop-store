package src.infra.repository;

import src.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    List<Car> getCar();

    Optional<Car> getById(int id);

    void save(Car car);

    void update(Car car);

    void delete(int id);

    int getNextId();
}
