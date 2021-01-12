package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
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
        IntStream.range(0, Storage.manufacturers.size())
                .filter(s -> Storage.manufacturers.get(s).getId().equals(manufacturer.getId()))
                .forEach(s -> Storage.manufacturers.set(s, manufacturer));
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.manufacturers.removeIf(m -> m.getId().equals(id));
    }
}
