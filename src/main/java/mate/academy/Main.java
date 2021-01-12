package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.Manufacturer;
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

        manufacturerService.create(manufacturerBmv);
        manufacturerService.create(manufacturerMercedes);
        manufacturerService.create(manufacturerAudi);

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
    }
}
