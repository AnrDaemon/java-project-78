package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

public final class Validator {

    public <K, T> MapSchema<K, T> map() {
        return new MapSchema<K, T>();
    }

    public NumberSchema number() {
        return new NumberSchema();
    }

    public StringSchema string() {
        return new StringSchema();
    }
}
