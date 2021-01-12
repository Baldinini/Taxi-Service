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
        Manufacturer oldManufacture = get(manufacturer.getId()).orElse(null);
        int index = Storage.manufacturers.indexOf(oldManufacture);
        Storage.manufacturers.set(index, manufacturer);
        return get(manufacturer.getId()).orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        Manufacturer manufacturer = get(id).orElse(null);
        return Storage.manufacturers.remove(manufacturer);
    }
}
