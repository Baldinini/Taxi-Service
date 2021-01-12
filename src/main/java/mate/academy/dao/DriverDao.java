package mate.academy.dao;

import java.util.List;
import mate.academy.model.Driver;

public interface DriverDao {
    Driver create(Driver driver);

    Driver get(Long id);

    List<Driver> getAll();

    Driver update(Driver driver);

    boolean delete(Long id);
}
