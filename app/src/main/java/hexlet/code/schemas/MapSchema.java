package hexlet.code.schemas;

import java.util.Map;

import lombok.Getter;

public final class MapSchema {

    @Getter
    private boolean isRequired = false;

    @Getter
    private Integer size = null;

    public MapSchema required() {
        this.isRequired = true;

        return this;
    }

    public MapSchema sizeof(Integer size) {
        this.size = size;

        return this;
    }

    public boolean isValid(Map data) {
        if (this.isRequired) {
            if (!(data instanceof Map)) {
                return false;
            }
        }

        if (this.size != null) {
            if (data == null || data.size() != this.size) {
                return false;
            }
        }

        return true;
    }
}
