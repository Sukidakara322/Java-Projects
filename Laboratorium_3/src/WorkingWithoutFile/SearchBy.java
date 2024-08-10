package WorkingWithoutFile;

public interface SearchBy<T> {
    boolean matches(T obj);

    boolean matches(Decision obj);
}

