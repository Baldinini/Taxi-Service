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
    private static final Long WRONG_ID = 10L;

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

        System.out.println("After updating");
        manufacturerService.getAll().forEach(System.out::println);

        manufacturerService.update(updateMercedes);

        boolean deleteRight = manufacturerService.delete(ID_FOR_DELETION);
        boolean deleteWrong = manufacturerService.delete(WRONG_ID);
        System.out.println(deleteRight);
        System.out.println(deleteWrong);

        System.out.println("After deletion");
        manufacturerService.getAll().forEach(System.out::println);

        DriverService driverService = (DriverService) injector.getInstance(DriverService.class);

        Driver driverPetro = new Driver("Petro", "A105326");
        Driver driverOlia = new Driver("Olia", "B532026");

        driverService.create(driverPetro);
        driverService.create(driverOlia);

        System.out.println("Drivers");
        driverService.getAll().forEach(System.out::println);

        Driver updateDriverPetro = driverService.get(1L);
        updateDriverPetro.setLicenceNumber("AA202325");

        driverService.update(updateDriverPetro);
        driverService.delete(2L);
        driverService.delete(10L);

        System.out.println("After deletion and updating");
        driverService.getAll().forEach(System.out::println);
        System.out.println(driverService.get(1L));

        CarService carService = (CarService) injector.getInstance(CarService.class);
        Car carAudiR8 = new Car("AudiR8", manufacturerAudi);
        Car carMercedesBenz = new Car("MercedesBenz", manufacturerMercedes);
        Car carToyota = new Car("Toyota C-HR", manufacturerToyota);

        carService.create(carAudiR8);
        carService.create(carMercedesBenz);
        carService.create(carToyota);

        System.out.println("Cars");
        carService.getAll().forEach(System.out::println);

        Car updateCarMercedes = carService.get(2L);
        updateCarMercedes.setModel("Mercedes-AMG");
        Driver driverKate = new Driver("Kate", "AA147896");
        driverService.create(driverKate);

        carService.addDriverToCar(driverKate, carAudiR8);
        carService.addDriverToCar(driverKate, carToyota);
        carService.addDriverToCar(driverPetro, carAudiR8);
        carService.addDriverToCar(driverOlia, carAudiR8);
        carService.update(updateCarMercedes);
        carService.addDriverToCar(driverKate, updateCarMercedes);

        System.out.println("Cars after adding drivers and updating");
        carService.getAll().forEach(System.out::println);

        carService.removeDriverFromCar(driverPetro, carAudiR8);
        carService.removeDriverFromCar(driverOlia, carService.get(2L));

        System.out.println("Cars after deletion drivers from cars");
        carService.getAll().forEach(System.out::println);

        List<Car> carByDriverKate = carService.getAllByDriver(driverKate.getId());
        List<Car> carByDriverPetro = carService.getAllByDriver(driverPetro.getId());

        System.out.println("Lists cars by drivers");
        carByDriverKate.forEach(System.out::println);
        carByDriverPetro.forEach(System.out::println);
    }
}
