package mate.academy.service.impl;

import java.util.ArrayList;
import java.util.List;
import mate.academy.dao.CarDao;
import mate.academy.dao.DriverDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.Car;
import mate.academy.model.Driver;
import mate.academy.service.CarService;

@Service
public class CarServiceImpl implements CarService {
    @Inject
    private CarDao carDao;
    @Inject
    private DriverDao driverDao;

    @Override
    public Car create(Car car) {
        return carDao.create(car);
    }

    @Override
    public Car get(Long id) {
        return carDao.get(id);
    }

    @Override
    public List<Car> getAll() {
        return carDao.getAll();
    }

    @Override
    public Car update(Car car) {
        return carDao.update(car);
    }

    @Override
    public boolean delete(Long id) {
        return carDao.delete(id);
    }

    @Override
    public void addDriverToCar(Driver driver, Car car) {
        if (!driverDao.getAll().contains(driver)) {
            driverDao.create(driver);
        }
        List<Driver> drivers = car.getDrivers() != null ? car.getDrivers() : new ArrayList<>();
        drivers.add(driver);
        car.setDrivers(drivers);
        carDao.update(car);
    }

    @Override
    public void removeDriverFromCar(Driver driver, Car car) {
        if (car.getDrivers() != null && car.getDrivers().contains(driver)) {
            car.getDrivers().remove(driver);
            carDao.update(car);
        } else {
            System.out.println("Car doesn't have this driver: " + driver);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return carDao.getAllByDriver(driverId);
    }
}
