package mate.academy.dao;

import java.util.List;
import mate.academy.model.Car;

public interface CarDao extends GenericDao<Car, Long> {
    List<Car> getAllByDriver(Long driverId);
}
