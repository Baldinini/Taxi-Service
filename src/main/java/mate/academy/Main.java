package mate.academy;

import java.util.List;
import mate.academy.lib.Injector;
import mate.academy.model.Car;
import mate.academy.model.Driver;
import mate.academy.model.Manufacturer;
import mate.academy.service.CarService;
import mate.academy.service.DriverService;
import mate.academy.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final Long ID_FOR_UPDATING = 2L;
    private static final Long ID_FOR_DELETION = 3L;

    public static void main(String[] args) {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);

        Manufacturer manufacturerBmv = new Manufacturer("BMV", "Bavaria");
        Manufacturer manufacturerMercedes = new Manufacturer("Mercedes", "Germany");
        Manufacturer manufacturerAudi = new Manufacturer("Audi R8", "Germany");
        Manufacturer manufacturerToyota = new Manufacturer("Toyota C-HR", "Japan");

        manufacturerService.create(manufacturerBmv);
        manufacturerService.create(manufacturerMercedes);
        manufacturerService.create(manufacturerAudi);
        manufacturerService.create(manufacturerToyota);

        manufacturerService.getAll().forEach(System.out::println);

        Manufacturer updateMercedes = manufacturerService.get(ID_FOR_UPDATING);
        updateMercedes.setName("MercedesBenz");
        manufacturerService.update(updateMercedes);

        System.out.println("After updating");
        manufacturerService.getAll().forEach(System.out::println);

        boolean deleteRight = manufacturerService.delete(ID_FOR_DELETION);
        System.out.println("Deletion manufacture from manufacturers table: " + deleteRight);

        System.out.println("After deletion");
        manufacturerService.getAll().forEach(System.out::println);

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);

        Driver driverPetro = new Driver("Petro", "A105326", "Petro", "1234");
        Driver driverOlia = new Driver("Olia", "B532026", "Olia", "1234");
        Driver driverKate = new Driver("Kate", "AA147896", "Kate", "1234");
        Driver driverAlex = new Driver("Alex", "AS2365DD", "Alex", "1234");

        driverService.create(driverKate);
        driverService.create(driverPetro);
        driverService.create(driverOlia);
        driverService.create(driverAlex);

        System.out.println("Drivers");
        driverService.getAll().forEach(System.out::println);

        Driver updateDriverPetro = driverService.get(1L);
        updateDriverPetro.setLicenceNumber("AA202325");

        driverService.update(updateDriverPetro);
        driverService.delete(2L);
        driverService.delete(10L);

        System.out.println("After deletion and updating");
        driverService.getAll().forEach(System.out::println);

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car carAudiR8 = new Car("AudiR8", manufacturerAudi);
        Car carMercedes = new Car("MercedesBenz", manufacturerMercedes);
        Car carToyota = new Car("Toyota C-HR", manufacturerToyota);

        carService.create(carAudiR8);
        carService.create(carMercedes);
        carService.create(carToyota);

        System.out.println("Cars");
        carService.getAll().forEach(System.out::println);

        Car updateCarMercedes = carService.get(carMercedes.getId());
        updateCarMercedes.setModel("Mercedes-AMG");

        carService.update(updateCarMercedes);
        carService.addDriverToCar(driverKate, carAudiR8);
        carService.addDriverToCar(driverOlia, carAudiR8);
        carService.addDriverToCar(driverKate, updateCarMercedes);
        carService.addDriverToCar(driverKate, carToyota);
        carService.addDriverToCar(driverOlia, carToyota);
        carService.addDriverToCar(driverOlia, updateCarMercedes);
        carService.addDriverToCar(driverAlex, carToyota);

        System.out.println("Cars after adding drivers and updating");
        carService.getAll().forEach(System.out::println);

        carService.removeDriverFromCar(driverKate, carAudiR8);

        System.out.println("Cars after deletion drivers from cars");
        carService.getAll().forEach(System.out::println);

        boolean delete = carService.delete(1L);
        System.out.println("Deletion car from cars table: " + delete);

        List<Car> carByDriverKate = carService.getAllByDriver(driverKate.getId());
        System.out.println("Lists cars by driver Kate");
        carByDriverKate.forEach(System.out::println);

        List<Car> carsByDriverOlia = carService.getAllByDriver(driverOlia.getId());
        System.out.println("Lists cars by driver Olia");
        carsByDriverOlia.forEach(System.out::println);
    }
}

