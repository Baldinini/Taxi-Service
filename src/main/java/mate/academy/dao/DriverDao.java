package mate.academy.dao;

import java.util.Optional;
import mate.academy.model.Driver;

public interface DriverDao extends GenericDao<Driver, Long> {
    Optional<Driver> findByLogin(String login);
}
