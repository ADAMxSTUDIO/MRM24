package dao;

public interface IClass<T> {
    void add(T o);
    T get(Long id);
    void modify(T o, Long id);
    void delete(Long id);
}
