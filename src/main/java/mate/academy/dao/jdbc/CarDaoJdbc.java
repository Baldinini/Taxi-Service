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
public class CarDaoJdbc implements CarDao {

    @Override
    public Car create(Car car) {
        String createQuery = "INSERT INTO cars (car_model, manufacturer_id) VALUES (?,?);";
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
                + "JOIN manufacturers m ON c.manufacturer_id = m.manufacturer_id "
                + "WHERE c.car_id = ? AND c.is_deleted = FALSE;";
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
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String getAllCar = "SELECT * FROM cars c "
                + "JOIN manufacturers m ON c.manufacturer_id = m.manufacturer_id "
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
        return cars;
    }

    @Override
    public Car update(Car car) {
        String updateCars = "UPDATE cars SET car_model = ?, manufacturer_id = ? "
                + "WHERE car_id = ? and is_deleted = FALSE;";
        String deleteCarsDrivers = "DELETE FROM cars_drivers WHERE car_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateCars);
                PreparedStatement statementDelete =
                        connection.prepareStatement(deleteCarsDrivers)) {
            statement.setString(1, car.getModel());
            statement.setLong(2, car.getManufacturer().getId());
            statement.setLong(3, car.getId());
            statement.executeUpdate();
            statementDelete.setLong(1, car.getId());
            statementDelete.executeUpdate();
            createCarsDrivers(car);
            return car;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update table with this car: " + car, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteQuery = "UPDATE cars SET is_deleted = TRUE WHERE car_id = ?;";
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
                + "JOIN cars c ON c.car_id = cd.car_id "
                + "JOIN manufacturers m ON c.manufacturer_id = m.manufacturer_id "
                + "WHERE cd.driver_id = ? AND c.is_deleted = FALSE;";
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
        return cars;
    }

    private Car getCar(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("manufacturer_id", Long.class);
        String manufacturerName = resultSet.getObject("manufacturer_name", String.class);
        String manufacturerCountry =
                resultSet.getObject("manufacturer_country", String.class);
        Manufacturer manufacturer = new Manufacturer(manufacturerName, manufacturerCountry);
        manufacturer.setId(manufacturerId);

        Long carId = resultSet.getObject("car_id", Long.class);
        String model = resultSet.getObject("car_model", String.class);
        Car car = new Car(model, manufacturer);
        car.setId(carId);
        car.setDrivers(getListDrivers(carId));
        return car;
    }

    private List<Driver> getListDrivers(Long carId) {
        String getDriversByCarId = "SELECT * FROM drivers d "
                + "JOIN cars_drivers cd ON cd.driver_id = d.driver_id "
                + "WHERE car_id = ? AND is_deleted = FALSE";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getDriversByCarId)) {
            statement.setLong(1, carId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long driverId = resultSet.getObject("driver_id", Long.class);
                String driverName = resultSet.getObject("driver_name", String.class);
                String driverLicenceNumber = resultSet.getObject("licence_number", String.class);
                Driver driver = new Driver(driverName, driverLicenceNumber);
                driver.setId(driverId);
                drivers.add(driver);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list of drivers by car_id: " + carId, e);
        }
        return drivers;
    }

    private void createCarsDrivers(Car car) {
        String createCarsDrivers = "INSERT INTO cars_drivers (car_id, driver_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(createCarsDrivers)) {
            for (int i = 0; i < car.getDrivers().size(); i++) {
                statement.setLong(1, car.getId());
                statement.setLong(2, car.getDrivers().get(i).getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update table cars_drivers by car: " + car, e);
        }
    }
}

