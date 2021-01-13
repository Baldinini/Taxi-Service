package mate.academy.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mate.academy.dao.CarDao;
import mate.academy.db.Storage;
import mate.academy.lib.Dao;
import mate.academy.model.Car;

@Dao
public class CarDaoImpl implements CarDao {

    @Override
    public Car create(Car car) {
        Storage.addCar(car);
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        return Storage.cars.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Car> getAll() {
        return Storage.cars;
    }

    @Override
    public Car update(Car car) {
        IntStream.range(0, Storage.cars.size())
                .filter(i -> Storage.cars.get(i).getId().equals(car.getId()))
                .forEach(i -> Storage.cars.set(i, car));
        return car;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.cars.removeIf(d -> d.getId().equals(id));
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return Storage.cars.stream()
                .filter(c -> c.getDrivers().stream()
                        .anyMatch(d -> d.getId().equals(driverId)))
                .collect(Collectors.toList());
    }
}
