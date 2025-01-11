package src.infra.repository;

import src.domain.model.Car;
import src.infra.adapters.CarRepositoryAdapter;
import src.infra.files.CarDatabaseFile;

import java.util.Optional;

public class CarRepositoryImp implements CarRepositoryAdapter {
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
    public int getNextId() {
        return this.file.getNextId();
    }
}
