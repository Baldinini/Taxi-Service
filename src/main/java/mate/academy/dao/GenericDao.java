package mate.academy.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T,I> {
    T create(T t);

    Optional<T> get(I id);

    List<T> getAll();

    T update(T t);

    boolean delete(I id);
}
