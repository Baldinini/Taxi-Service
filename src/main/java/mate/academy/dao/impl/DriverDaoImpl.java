package mate.academy.dao.impl;

import java.util.List;
import java.util.stream.IntStream;
import mate.academy.dao.DriverDao;
import mate.academy.db.Storage;
import mate.academy.lib.Dao;
import mate.academy.model.Driver;

@Dao
public class DriverDaoImpl implements DriverDao {
    @Override
    public Driver create(Driver driver) {
        Storage.addDriver(driver);
        return driver;
    }

    @Override
    public Driver get(Long id) {
        return Storage.drivers.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Driver> getAll() {
        return Storage.drivers;
    }

    @Override
    public Driver update(Driver driver) {
        IntStream.range(0, Storage.drivers.size())
                .filter(i -> Storage.drivers.get(i).getId().equals(driver.getId()))
                .forEach(i -> Storage.drivers.set(i, driver));
        return driver;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.drivers.removeIf(d -> d.getId().equals(id));
    }
}
