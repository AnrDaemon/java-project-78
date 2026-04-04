package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

abstract public class BaseSchema<T> {

    private final Map<String, Predicate<T>> predicates = new LinkedHashMap<>(
            Map.ofEntries(Map.entry("default", (v) -> true)));

    protected final void put(String key, Predicate<T> predicate) {
        predicates.put(key, predicate);
    }

    public final boolean isValid(T value) {
        return predicates.entrySet().stream().allMatch((v) -> v.getValue().test(value));
    }
}
