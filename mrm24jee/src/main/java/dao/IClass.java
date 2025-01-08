package dao;

import java.util.List;

public interface IClass<T> {
    void add(T o);
    T get(Long id);
    List<T> getAll();
    void modify(T o, Long id);
    void delete(Long id);
}
