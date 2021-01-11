package mate.academy.model;

public class Driver {
    private Long id;
    private String name;
    private String licenceNumber;

    public Driver(String name, String licenceNumber) {
        this.name = name;
        this.licenceNumber = licenceNumber;
    }
}
