package src.service.impl;

import src.domain.Car;
import src.infra.repository.CarRepository;
import src.infra.repository.impl.CarRepositoryImp;
import src.service.CarService;

import java.util.Optional;

public class CarServiceImpl implements CarService {

    private final CarRepository carRepository = new CarRepositoryImp();

    @Override
    public Optional<Car> getACar() {
        return carRepository.getCar();
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }


    @Override
    public void update(Car car) {
        carRepository.update(car);
    }
}
