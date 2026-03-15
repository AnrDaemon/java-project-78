package hexlet.code;

import org.junit.jupiter.api.Test;

import hexlet.code.schemas.StringSchema;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    @Test
    void validatorCreatesStringValidator() {
        var v = new Validator();
        assertInstanceOf(StringSchema.class, v.string(), "Validator must have string schema");
    }
}
