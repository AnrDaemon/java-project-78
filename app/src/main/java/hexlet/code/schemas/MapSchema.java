package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<String, String>> {
    public MapSchema required() {
        this.put("required", (data) -> data instanceof Map);

        return this;
    }

    public MapSchema sizeof(Integer size) {
        this.required();
        this.put("required", (data) -> (data != null && data.size() == size));

        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> shape) {
        this.put("shape", (data) -> data != null && shape.entrySet().stream()
                .allMatch((v) -> v.getValue().isValid(data.get(v.getKey()))));

        return this;
    }
}
