package mate.academy.service;

import java.util.List;
import mate.academy.model.Car;
import mate.academy.model.Driver;

public interface CarService extends GenericService<Car, Long> {
    void addDriverToCar(Driver driver, Car car);

    void removeDriverFromCar(Driver driver, Car car);

    List<Car> getAllByDriver(Long driverId);
}
