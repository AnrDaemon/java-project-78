package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import hexlet.code.schemas.NumberSchema;

public class NumberSchemaTest extends FileReadingTest {

    private static Validator v;

    private NumberSchema schema;

    @BeforeAll
    static void init() {
        FileReadingTest.init();

        v = new Validator();
    }

    @BeforeEach
    void prepare() {
        this.schema = v.number();
    }

    /**
     * Test source data generator.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> isRequiredSourceData() {
        return Stream.of(//
                Arguments.of(false, null, true, "Message for null valid value"), //
                Arguments.of(false, 0, true, "Message for empty valid value"), //
                Arguments.of(false, -1, true, "Message for negative valid non-required value"), //
                Arguments.of(false, 5, true, "Message for positive valid non-required value"), //
                //
                Arguments.of(true, null, false, "Message for null invalid value"), //
                Arguments.of(true, 0, false, "Message for empty invalid value"), //
                Arguments.of(true, -1, true, "Message for negative valid value"), //
                Arguments.of(true, 5, true, "Message for positive valid value") //
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
    void schemaIsRequired(Boolean flag, Integer src, Boolean expected, String message) throws Exception {
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
    static Stream<Arguments> isPositiveSourceData() {
        return Stream.of(
                // Null positive
                Arguments.of(false, null, true, "Message for null valid value"), //
                Arguments.of(false, 0, true, "Message for empty valid value"), //
                Arguments.of(false, -1, true, "Message for negative valid non-required value"), //
                Arguments.of(false, 5, true, "Message for positive valid non-required value"), //
                //
                Arguments.of(true, null, false, "Message for null invalid value"), //
                Arguments.of(true, 0, false, "Message for empty invalid value"), //
                Arguments.of(true, -1, false, "Message for negative invalid value"), //
                Arguments.of(true, 5, true, "Message for positive valid value") //
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
    @MethodSource("isPositiveSourceData")
    void schemaIsPositive(Boolean flag, Integer src, Boolean expected, String message) throws Exception {
        if (flag) {
            this.schema.positive();
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }

    /**
     * Test source data generator.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> hasRangeSourceData() {
        return Stream.of(
                // Null positive
                Arguments.of(null, null, null, true, "Message for null valid value"), //
                Arguments.of(null, null, 0, true, "Message for zero valid value"), //
                Arguments.of(null, null, -1, true, "Message for negative valid non-required value"), //
                Arguments.of(null, null, 5, true, "Message for positive valid non-required value"), //
                // [0, inf)
                Arguments.of(0, null, null, false, "Message for null invalid value"), //
                Arguments.of(0, null, 0, true, "Message for zero valid value"), //
                Arguments.of(0, null, -1, false, "Message for negative invalid value"), //
                Arguments.of(0, null, 5, true, "Message for positive valid value"), //
                // (inf, 0]
                Arguments.of(null, 0, null, false, "Message for null invalid value"), //
                Arguments.of(null, 0, 0, true, "Message for zero valid value"), //
                Arguments.of(null, 0, -1, true, "Message for negative valid value"), //
                Arguments.of(null, 0, 5, false, "Message for positive invalid value"), //
                // [0, 1]
                Arguments.of(0, 1, null, false, "Message for null invalid value"), //
                Arguments.of(0, 1, 0, true, "Message for zero valid value"), //
                Arguments.of(0, 1, -1, false, "Message for negative invalid value"), //
                Arguments.of(0, 1, 5, false, "Message for positive invalid value"), //
                // [-5, 5]
                Arguments.of(-5, 5, null, false, "Message for null invalid value"), //
                Arguments.of(-5, 5, 0, true, "Message for zero valid value"), //
                Arguments.of(-5, 5, -1, true, "Message for negative valid value"), //
                Arguments.of(-5, 5, 5, true, "Message for positive valid value"), //
                // [5, 6]
                Arguments.of(5, 6, null, false, "Message for null invalid value"), //
                Arguments.of(5, 6, 0, false, "Message for zero invalid value"), //
                Arguments.of(5, 6, -1, false, "Message for negative invalid value"), //
                Arguments.of(5, 6, 5, true, "Message for positive valid value") //
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
    @MethodSource("hasRangeSourceData")
    void schemaHasRange(Integer min, Integer max, Integer src, Boolean expected, String message) throws Exception {
        if (min != null || max != null) {
            this.schema.range(min, max);
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }
}
