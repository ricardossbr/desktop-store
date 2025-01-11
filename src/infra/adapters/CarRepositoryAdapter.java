package src.infra.adapters;

import src.domain.model.Car;

import java.util.Optional;

public interface CarRepositoryAdapter {

    Optional<Car> getCar();

    void save(Car car);

    void update(Car car);

    int getNextId();
}
