package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    public MapSchema required() {
        this.put("required", (data) -> data instanceof Map);

        return this;
    }

    public MapSchema sizeof(Integer size) {
        this.required();
        this.put("sizeof", (data) -> data.size() == size);

        return this;
    }

    public <T> MapSchema shape(Map<String, BaseSchema<T>> shape) {
        this.put("shape", (data) -> data != null && shape.entrySet().stream()
                .allMatch((v) -> v.getValue().isValid((T) data.get(v.getKey()))));

        return this;
    }
}
