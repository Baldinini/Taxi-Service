package mate.academy.service;

import java.util.Optional;
import mate.academy.model.Driver;

public interface DriverService extends GenericService<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
