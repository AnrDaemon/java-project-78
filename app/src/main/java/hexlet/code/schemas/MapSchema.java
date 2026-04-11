package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema<T> extends BaseSchema<Map<String, T>> {
    public MapSchema<T> required() {
        this.put("required", (data) -> data instanceof Map);

        return this;
    }

    public MapSchema<T> sizeof(Integer size) {
        this.required();
        this.put("sizeof", (data) -> data.size() == size);

        return this;
    }

    public MapSchema<T> shape(Map<String, BaseSchema<T>> shape) {
        this.put("shape", (data) -> data != null
                && shape.entrySet().stream().allMatch((v) -> v.getValue().isValid(data.get(v.getKey()))));

        return this;
    }
}
