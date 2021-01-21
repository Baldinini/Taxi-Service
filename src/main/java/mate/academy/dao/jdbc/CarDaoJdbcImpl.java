package mate.academy.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.CarDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Car;
import mate.academy.model.Driver;
import mate.academy.model.Manufacturer;
import mate.academy.util.ConnectionUtil;

@Dao
public class CarDaoJdbcImpl implements CarDao {
    @Override
    public Car create(Car car) {
        String createQuery = "INSERT INTO cars (model, manufacturer_id) VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statementForCar =
                        connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statementForCar.setString(1, car.getModel());
            statementForCar.setLong(2, car.getManufacturer().getId());
            statementForCar.executeUpdate();
            ResultSet resultSet = statementForCar.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create car: " + car, e);
        }
    }

    @Override
    public Optional<Car> get(Long id) {
        String getById = "SELECT * FROM cars c "
                + "JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE c.id = ? AND c.is_deleted = FALSE;";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statementCar = connection.prepareStatement(getById)) {
            statementCar.setLong(1, id);
            ResultSet resultSet = statementCar.executeQuery();
            if (resultSet.next()) {
                car = getCar(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get car by id: " + id, e);
        }
        if (car != null) {
            car.setDrivers(getListDrivers(car.getId()));
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String getAllCar = "SELECT * FROM cars c "
                + "JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "WHERE c.is_deleted = FALSE;";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getAllCar)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of car", e);
        }
        for (Car car : cars) {
            car.setDrivers(getListDrivers(car.getId()));
        }
        return cars;
    }

    @Override
    public Car update(Car car) {
        String updateCars = "UPDATE cars SET model = ?, manufacturer_id = ? "
                + "WHERE id = ? and is_deleted = FALSE;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateCars)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.setLong(3, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update table with this car: " + car, e);
        }
        deleteCarsDrivers(car);
        createCarsDrivers(car);
        return car;
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE cars SET is_deleted = TRUE WHERE id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete car by id: " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String getCars = "SELECT * FROM cars_drivers cd "
                + "JOIN cars c ON c.id = cd.car_id "
                + "JOIN manufacturers m ON c.manufacturer_id = m.id "
                + "JOIN drivers d ON cd.driver_id = d.id "
                + "WHERE cd.driver_id = ? AND c.is_deleted = FALSE AND d.is_deleted = FALSE;";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getCars)) {
            statement.setLong(1, driverId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cars.add(getCar(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of cars by driverId: " + driverId, e);
        }
        for (Car car : cars) {
            car.setDrivers(getListDrivers(car.getId()));
        }
        return cars;
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
        String manufacturerName = resultSet.getObject("name", String.class);
        String manufacturerCountry =
                resultSet.getObject("country", String.class);
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);

        Long carId = resultSet.getObject("id", Long.class);
        String model = resultSet.getObject("model", String.class);
        Car car = new Car(model, manufacturer);
        car.setId(carId);
        return car;
    }

    private List<Driver> getListDrivers(Long carId) {
        String getDriversByCarId = "SELECT * FROM drivers d "
                + "JOIN cars_drivers cd ON cd.driver_id = d.id "
                + "WHERE car_id = ? AND is_deleted = FALSE";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getDriversByCarId)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long driverId = resultSet.getObject("driver_id", Long.class);
                String driverName = resultSet.getObject("name", String.class);
                String driverLicenceNumber = resultSet.getObject("licence_number", String.class);
                String driverLogin = resultSet.getObject("login", String.class);
                String driverPassword = resultSet.getObject("password", String.class);
                Driver driver =
                        new Driver(driverName, driverLicenceNumber, driverLogin, driverPassword);
                driver.setId(driverId);
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of drivers by car_id: " + carId, e);
        }
        return drivers;
    }

    private void deleteCarsDrivers(Car car) {
        String deleteCarsDrivers = "DELETE FROM cars_drivers WHERE car_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteCarsDrivers)) {
            statement.setLong(1, car.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update table cars_drivers by car: " + car, e);
        }
    }

    private void createCarsDrivers(Car car) {
        String createCarsDrivers = "INSERT INTO cars_drivers (car_id, driver_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(createCarsDrivers)) {
            for (Driver driver : car.getDrivers()) {
                statement.setLong(1, car.getId());
                statement.setLong(2, driver.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update table cars_drivers by car: " + car, e);
        }
    }
}
