package src.infra.repository.impl;

import src.domain.Car;
import src.infra.files.CarDatabaseFile;
import src.infra.repository.CarRepository;

import java.util.List;

public class CarRepositoryImp implements CarRepository {
    private final CarDatabaseFile file = new CarDatabaseFile();


    @Override
    public List<Car> getCar() {
        return file.readFile();
    }

    @Override
    public Car getById(int id) {
        return file.getLineById(id);
    }

    @Override
    public void save(Car car) {
        this.file.writerFile(car.toString());
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
