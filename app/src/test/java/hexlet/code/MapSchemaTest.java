package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;

public class MapSchemaTest extends FileReadingTest {

    private static Validator v;

    private MapSchema schema;

    @BeforeAll
    static void init() {
        FileReadingTest.init();

        v = new Validator();
    }

    @BeforeEach
    void prepare() {
        this.schema = v.map();
    }

    /**
     * Test source data generator for .required() validation.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> isRequiredSourceData() {
        var a = new HashMap<>();
        a.put("0", "a");

        return Stream.of(//
                Arguments.of(false, null, true, "Message for null unchecked value"), //
                Arguments.of(false, new HashMap<>(), true, "Message for empty unchecked value"), //
                Arguments.of(false, a, true, "Message for non-empty unchecked value"), //
                //
                Arguments.of(true, null, false, "Message for null invalid value"), //
                Arguments.of(true, new HashMap<>(), true, "Message for empty valid value"), //
                Arguments.of(true, a, true, "Message for non-empty valid value") //
        );
    }

    /**
     * Test for .required() validation.
     */
    @ParameterizedTest
    @MethodSource("isRequiredSourceData")
    void schemaIsRequired(Boolean flag, Map<String, String> src, Boolean expected, String message) throws Exception {
        if (flag) {
            this.schema.required();
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }

    /**
     * Test source data generator for .sizeof() validation.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> sizeOfSourceData() {
        var a = new HashMap<>();
        a.put("0", "a");

        var b = new HashMap<>();
        b.put("0", "a");
        b.put("1", "b");

        return Stream.of(
                // Null minLength
                Arguments.of(null, null, true, "Message for null unchecked value"), //
                Arguments.of(null, new HashMap<>(), true, "Message for empty unchecked value"), //
                Arguments.of(null, a, true, "Message for small unchecked value"), //
                Arguments.of(null, b, true, "Message for large unchecked value"), //

                // Zero minLength
                Arguments.of(0, null, false, "Message for null invalid(0) value"),
                Arguments.of(0, new HashMap<>(), true, "Message for empty valid value"), //
                Arguments.of(0, a, false, "Message for small invalid value"), //
                Arguments.of(0, b, false, "Message for large invalid(0) value"), //

                // Zero minLength
                Arguments.of(1, null, false, "Message for null invalid(1) value"),
                Arguments.of(1, new HashMap<>(), false, "Message for empty invalid value"), //
                Arguments.of(1, a, true, "Message for small valid value"), //
                Arguments.of(1, b, false, "Message for large invalid(1) value") //
        );
    }

    /**
     * Test for .sizeof() validation.
     */
    @ParameterizedTest
    @MethodSource("sizeOfSourceData")
    void schemaIsOfSize(Integer length, Map<String, String> src, Boolean expected, String message) throws Exception {
        if (length != null) {
            this.schema.sizeof(length);
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }

    /**
     * Test source data generator for .shape() validation.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> shapeSourceData() {
        var stringSchemaUnchecked = v.string();
        var stringSchemaRequired = v.string().required();
        var stringSchemaMinLength = v.string().minLength(3);

        // Test data maps
        var emptyMap = new HashMap<String, String>();
        var validMapMin = new HashMap<String, String>(Map.of( //
                "name", "John", //
                "city", "Moscow" //
        ));

        var validMapExtra = new HashMap<String, String>(Map.of( //
                "name", "Alice", //
                "city", "London", //
                "extra", "ignored" // extra field should be ignored
        ));

        var invalidMapEmptyName = new HashMap<String, String>(Map.of( //
                "name", "", // empty string for required field
                "city", "Paris" //
        ));

        var invalidMapShortCity = new HashMap<String, String>(Map.of( //
                "name", "Bob", //
                "city", "NY" // city too short for minLength(3)
        ));

        var invalidMapMissingField = new HashMap<String, String>(Map.of( //
                "city", "Berlin" // missing 'name' field
        ));

        // Shape definitions
        var shapeUnchecked = new HashMap<String, BaseSchema<String>>(Map.of( //
                "name", stringSchemaUnchecked, //
                "city", stringSchemaUnchecked //
        ));

        var shapeRequired = new HashMap<String, BaseSchema<String>>(Map.of( //
                "name", stringSchemaRequired, //
                "city", stringSchemaUnchecked //
        ));

        var shapeComplex = new HashMap<String, BaseSchema<String>>(Map.of( //
                "name", stringSchemaRequired, //
                "city", stringSchemaMinLength //
        ));

        return Stream.of(
                Arguments.of(new HashMap<>(), emptyMap, true, "Empty shape accepts empty map"), //
                Arguments.of(new HashMap<>(), validMapMin, true, "Empty shape accepts any map"), //

                Arguments.of(shapeUnchecked, null, false, "Basic shape: Null input should fail validation"), //
                Arguments.of(shapeUnchecked, emptyMap, true,
                        "Basic shape: Empty map is valid when all fields are optional"), //
                Arguments.of(shapeUnchecked, validMapMin, true, "Basic shape: All fields present with valid values"), //
                Arguments.of(shapeUnchecked, validMapExtra, true, "Basic shape: Extra fields are ignored"), //
                Arguments.of(shapeUnchecked, invalidMapEmptyName, true,
                        "Basic shape: Extra fields are ignored (empty value doesn't matter for unchecked schema)"), //
                Arguments.of(shapeUnchecked, invalidMapShortCity, true,
                        "Basic shape: Extra fields are ignored (short value doesn't matter for unchecked schema)"), //
                Arguments.of(shapeUnchecked, invalidMapMissingField, true,
                        "Basic shape: Extra fields are ignored (missing field doesn't matter for unchecked schema)"), //

                Arguments.of(shapeRequired, invalidMapMissingField, false,
                        "Required field 'name' is missing from the map"), //
                Arguments.of(shapeRequired, invalidMapEmptyName, false,
                        "Required field 'name' has invalid value (empty string)"), //

                Arguments.of(shapeComplex, validMapMin, true,
                        "Complex shape: All constraints satisfied (required field present and city length ≥ 3)"), //
                Arguments.of(shapeComplex, invalidMapShortCity, false,
                        "Complex shape: 'city' field too short (length < 3, required by minLength constraint)") //
        );
    }

    /**
     * Test for .shape() validation.
     *
     * @param shape    The shape definition to test
     * @param src      The input map to validate
     * @param expected Expected validation result
     * @param message  Failure message
     */
    @ParameterizedTest
    @MethodSource("shapeSourceData")
    void schemaShapeValidation(Map<String, BaseSchema<String>> shape, Map<String, String> src, Boolean expected,
            String message) throws Exception {
        if (shape != null) {
            this.schema.shape(shape);
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }
}
