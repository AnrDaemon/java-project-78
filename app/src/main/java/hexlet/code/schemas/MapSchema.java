package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema<K, T> extends BaseSchema<Map<K, T>> {
    public MapSchema<K, T> required() {
        this.put("required", (data) -> data instanceof Map);

        return this;
    }

    public MapSchema<K, T> sizeof(Integer size) {
        this.required();
        this.put("sizeof", (data) -> data.size() == size);

        return this;
    }

    public MapSchema<K, T> shape(Map<K, BaseSchema<T>> shape) {
        this.put("shape", (data) -> data != null
                && shape.entrySet().stream().allMatch((v) -> v.getValue().isValid(data.get(v.getKey()))));

        return this;
    }
}
