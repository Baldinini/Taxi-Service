package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.db.Storage;
import mate.academy.lib.Dao;
import mate.academy.model.Manufacturer;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        Storage.addManufacturer(manufacturer);
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Storage.manufacturers.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Manufacturer> getAll() {
        return Storage.manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        get(manufacturer.getId()).get().setCountry(manufacturer.getCountry());
        get(manufacturer.getId()).get().setName(manufacturer.getName());
        return get(manufacturer.getId()).get();
    }

    @Override
    public boolean delete(Long id) {
        if (get(id).isPresent()) {
            Storage.manufacturers.remove(get(id).get());
            return true;
        }
        return false;
    }
}
