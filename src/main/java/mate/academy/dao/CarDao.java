package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Car;

public interface CarDao {
    Car create(Car car);

    Optional<Car> get(Long id);

    List<Car> getAll();

    Car update(Car car);

    boolean delete(Long id);

    List<Car> getAllByDriver(Long driverId);
}
