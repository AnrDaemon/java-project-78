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
     * Test source data generator.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> isRequiredSourceData() {
        var a = new HashMap<>();
        a.put("0", "a");

        return Stream.of(//
                Arguments.of(false, null, true, "Message for null valid value"), //
                Arguments.of(false, new HashMap<>(), true, "Message for empty valid value"), //
                Arguments.of(false, a, true, "Message for non-empty valid non-required value"), //
                //
                Arguments.of(true, null, false, "Message for null invalid value"), //
                Arguments.of(true, new HashMap<>(), true, "Message for empty valid value"), //
                Arguments.of(true, a, true, "Message for non-empty valid value") //
        );
    }

    /**
     * Test default generation with 2 paths.
     *
     * @param path1   Left file path.
     * @param path2   Right file path.
     * @param fixture Fixture file name.
     */
    @ParameterizedTest
    @MethodSource("isRequiredSourceData")
    void schemaIsRequired(Boolean flag, Map src, Boolean expected, String message) throws Exception {
        if (flag) {
            this.schema.required();
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }

    /**
     * Test source data generator.
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
                Arguments.of(null, null, true, "Message for null valid value"), //
                Arguments.of(null, new HashMap<>(), true, "Message for empty valid value"), //
                Arguments.of(null, a, true, "Message for small valid value"), //
                Arguments.of(null, b, true, "Message for large valid value"), //

                // Zero minLength
                Arguments.of(0, null, false, "Message for null invalid value"),
                Arguments.of(0, new HashMap<>(), true, "Message for empty valid value"), //
                Arguments.of(0, a, false, "Message for small invalid value"), //
                Arguments.of(0, b, false, "Message for large invalid value"), //

                // Zero minLength
                Arguments.of(1, null, false, "Message for null invalid value"),
                Arguments.of(1, new HashMap<>(), false, "Message for empty invalid value"), //
                Arguments.of(1, a, true, "Message for small valid value"), //
                Arguments.of(1, b, false, "Message for large invalid value") //
        );
    }

    /**
     * Test default generation with 2 paths.
     *
     * @param path1   Left file path.
     * @param path2   Right file path.
     * @param fixture Fixture file name.
     */
    @ParameterizedTest
    @MethodSource("sizeOfSourceData")
    void schemaHasMinLength(Integer length, Map src, Boolean expected, String message) throws Exception {
        if (length != null) {
            this.schema.sizeof(length);
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }
}
