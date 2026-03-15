package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import hexlet.code.schemas.StringSchema;

public class StringSchemaTest extends FileReadingTest {

    private static Validator v;

    private StringSchema schema;

    @BeforeAll
    static void init() {
        FileReadingTest.init();

        v = new Validator();
    }

    @BeforeEach
    void prepare() {
        this.schema = v.string();
    }

    /**
     * Test source data generator.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> isRequiredSourceData() {
        return Stream.of(//
                Arguments.of(false, null, true, "Message for null valid value"), //
                Arguments.of(false, "", true, "Message for empty valid value"), //
                Arguments.of(false, "text", true, "Message for non-empty valid non-required value"), //
                Arguments.of(true, null, false, "Message for null invalid value"), //
                Arguments.of(true, "", false, "Message for empty invalid value"), //
                Arguments.of(true, "text", true, "Message for non-empty valid value") //
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
    void schemaIsRequired(Boolean flag, String src, Boolean expected, String message) throws Exception {
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
    static Stream<Arguments> minLengthSourceData() {
        return Stream.of(
                // Null minLength
                Arguments.of(null, null, true, "Message for null valid value"), //
                Arguments.of(null, "", true, "Message for empty valid value"), //
                Arguments.of(null, "text", true, "Message for non-empty valid non-required value"), //

                // Zero minLength
                Arguments.of(0, null, false, "Message for null invalid value"), // @TODO Find out if null string is
                // valid if minLength is 0
                Arguments.of(0, "", true, "Message for empty valid value"), //
                Arguments.of(0, "text", true, "Message for non-empty valid value"), //

                // Non-zero minLength
                Arguments.of(1, null, false, "Message for null invalid value"), //
                Arguments.of(1, "", false, "Message for empty invalid value"), //
                Arguments.of(1, "text", true, "Message for non-empty valid value") //
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
    @MethodSource("minLengthSourceData")
    void schemaHasMinLength(Integer length, String src, Boolean expected, String message) throws Exception {
        if (length != null) {
            this.schema.minLength(length);
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }
}
