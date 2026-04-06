package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

class ValidatorTest {

    private static Validator v;

    /**
     * Prepare initial validator for tests.
     */
    @BeforeAll
    static void init() {
        v = new Validator();
    }

    @Test
    void validatorCreatesStringValidator() {
        assertInstanceOf(StringSchema.class, v.string(), "Validator must have string schema");
    }

    @Test
    void validatorCreatesNumberValidator() {
        assertInstanceOf(NumberSchema.class, v.number(), "Validator must have number schema");
    }

    @Test
    void validatorCreatesMapValidator() {
        assertInstanceOf(MapSchema.class, v.map(), "Validator must have map schema");
    }
}
