package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

public final class Validator {

    public NumberSchema number() {
        return new NumberSchema();
    }

    public StringSchema string() {
        return new StringSchema();
    }
}
