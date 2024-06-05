package src.infra.repository.impl;

import src.domain.Car;
import src.infra.files.CarDatabaseFile;
import src.infra.repository.CarRepository;

import java.util.Optional;

public class CarRepositoryImp implements CarRepository {
    private final CarDatabaseFile file = new CarDatabaseFile();

    @Override
    public Optional<Car> getCar() {
        return file.readFile();
    }

    @Override
    public void save(Car car) {
        this.file.editLineById(car);
    }

    @Override
    public void update(Car car) {
        this.file.editLineById(car);
    }

    @Override
    public void delete(int id) {
        this.delete(id);
    }

    @Override
    public int getNextId() {
        return this.file.getNextId();
    }
}
