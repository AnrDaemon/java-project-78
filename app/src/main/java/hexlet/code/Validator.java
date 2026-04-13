package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

public final class Validator {

    public MapSchema map() {
        return new MapSchema();
    }

    public NumberSchema number() {
        return new NumberSchema();
    }

    public StringSchema string() {
        return new StringSchema();
    }
}
