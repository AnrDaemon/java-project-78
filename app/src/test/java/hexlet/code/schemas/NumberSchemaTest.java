package hexlet.code.schemas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import hexlet.code.Validator;

public class NumberSchemaTest {

    private static Validator v;

    private NumberSchema schema;

    /**
     * Prepare initial validator for tests.
     */
    @BeforeAll
    static void init() {
        v = new Validator();
    }

    /**
     * Prepare initial schema for tests.
     */
    @BeforeEach
    void prepare() {
        this.schema = v.number();
    }

    /**
     * Test source data generator.
     *
     * @return Test arguments.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    static Stream<Arguments> isRequiredSourceData() {
        return Stream.of(
                // data
                Arguments.of(false, null, true, "Message for null unchecked value"), //
                Arguments.of(false, 0, true, "Message for empty unchecked value"), //
                Arguments.of(false, -1, true, "Message for negative unchecked value"), //
                Arguments.of(false, 5, true, "Message for positive vunchecked value"), //
                //
                Arguments.of(true, null, false, "Message for null invalid value"), //
                Arguments.of(true, 0, true, "Message for empty valid value"), //
                Arguments.of(true, -1, true, "Message for negative valid value"), //
                Arguments.of(true, 5, true, "Message for positive valid value") //
        );
    }

    /**
     * Test validation for required data.
     *
     * @param flag     If a shema is required.
     * @param src      Source schema.
     * @param expected Expected validation result.
     * @param message  Failed test message.
     */
    @ParameterizedTest
    @MethodSource("isRequiredSourceData")
    void schemaIsRequired(Boolean flag, Integer src, Boolean expected, String message) {
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
    @SuppressWarnings("checkstyle:MagicNumber")
    static Stream<Arguments> isNotEmptySourceData() {
        return Stream.of(
                // data
                Arguments.of(false, null, true, "Message for null unchecked value"), //
                Arguments.of(false, 0, true, "Message for empty unchecked value"), //
                Arguments.of(false, -1, true, "Message for negative unchecked value"), //
                Arguments.of(false, 5, true, "Message for positive vunchecked value"), //
                //
                Arguments.of(true, null, true, "Message for null valid value"), //
                Arguments.of(true, 0, false, "Message for empty invalid value"), //
                Arguments.of(true, -1, true, "Message for negative valid value"), //
                Arguments.of(true, 5, true, "Message for positive valid value") //
        );
    }

    /**
     * Test validation for required data.
     *
     * @param flag     If a shema is required.
     * @param src      Source schema.
     * @param expected Expected validation result.
     * @param message  Failed test message.
     */
    @ParameterizedTest
    @MethodSource("isNotEmptySourceData")
    void schemaIsNotEmpty(Boolean flag, Integer src, Boolean expected, String message) {
        if (flag) {
            this.schema.notEmpty();
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }

    /**
     * Test source data generator.
     *
     * @return Test arguments.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    static Stream<Arguments> isPositiveSourceData() {
        return Stream.of(
                // Null positive
                Arguments.of(false, null, true, "Message for null valid value"), //
                Arguments.of(false, 0, true, "Message for empty valid value"), //
                Arguments.of(false, -1, true, "Message for negative valid non-required value"), //
                Arguments.of(false, 5, true, "Message for positive valid non-required value"), //
                //
                Arguments.of(true, null, true, "Message for null valid value"), //
                Arguments.of(true, 0, false, "Message for empty invalid value"), //
                Arguments.of(true, -1, false, "Message for negative invalid value"), //
                Arguments.of(true, 5, true, "Message for positive valid value") //
        );
    }

    /**
     * Test validation for positive data.
     *
     * @param flag     If a shema should be positive.
     * @param src      Source schema.
     * @param expected Expected validation result.
     * @param message  Failed test message.
     */
    @ParameterizedTest
    @MethodSource("isPositiveSourceData")
    void schemaIsPositive(Boolean flag, Integer src, Boolean expected, String message) {
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
    @SuppressWarnings("checkstyle:MagicNumber")
    static Stream<Arguments> isNegativeSourceData() {
        return Stream.of(
                // Null positive
                Arguments.of(false, null, true, "Message for null unchecked value"), //
                Arguments.of(false, 0, true, "Message for empty unchecked value"), //
                Arguments.of(false, -1, true, "Message for negative unchecked value"), //
                Arguments.of(false, 5, true, "Message for positive unchecked value"), //
                //
                Arguments.of(true, null, true, "Message for null valid value"), //
                Arguments.of(true, 0, false, "Message for empty invalid value"), //
                Arguments.of(true, -1, true, "Message for negative valid value"), //
                Arguments.of(true, 5, false, "Message for positive invalid value") //
        );
    }

    /**
     * Test validation for negative data.
     *
     * @param flag     If a shema should be negative.
     * @param src      Source schema.
     * @param expected Expected validation result.
     * @param message  Failed test message.
     */
    @ParameterizedTest
    @MethodSource("isNegativeSourceData")
    void schemaIsNegative(Boolean flag, Integer src, Boolean expected, String message) {
        if (flag) {
            this.schema.negative();
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }

    /**
     * Test source data generator.
     *
     * @return Test arguments.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    static Stream<Arguments> hasRangeSourceData() {
        return Stream.of(
                // Null positive
                Arguments.of(null, null, null, true, "Message for null unchecked value"), //
                Arguments.of(null, null, 0, true, "Message for zero unchecked value"), //
                Arguments.of(null, null, -1, true, "Message for negative unchecked value"), //
                Arguments.of(null, null, 5, true, "Message for positive unchecked value"), //
                // [0, inf)
                Arguments.of(0, null, null, true, "Message for null valid[0,) value"), //
                Arguments.of(0, null, 0, true, "Message for zero valid[0,) value"), //
                Arguments.of(0, null, -1, false, "Message for negative invalid[0,) value"), //
                Arguments.of(0, null, 5, true, "Message for positive valid[0,) value"), //
                // (inf, 0]
                Arguments.of(null, 0, null, true, "Message for null valid(,0] value"), //
                Arguments.of(null, 0, 0, true, "Message for zero valid(,0] value"), //
                Arguments.of(null, 0, -1, true, "Message for negative valid(,0] value"), //
                Arguments.of(null, 0, 5, false, "Message for positive invalid(,0] value"), //
                // [0, 1]
                Arguments.of(0, 1, null, true, "Message for null valid[0,1] value"), //
                Arguments.of(0, 1, 0, true, "Message for zero valid[0,1] value"), //
                Arguments.of(0, 1, -1, false, "Message for negative invalid[0,1] value"), //
                Arguments.of(0, 1, 5, false, "Message for positive invalid[0,1] value"), //
                // [-5, 5]
                Arguments.of(-5, 5, null, true, "Message for null valid[-5,5] value"), //
                Arguments.of(-5, 5, 0, true, "Message for zero valid[-5,5] value"), //
                Arguments.of(-5, 5, -1, true, "Message for negative valid[-5,5] value"), //
                Arguments.of(-5, 5, 5, true, "Message for positive valid[-5,5] value"), //
                // [5, 6]
                Arguments.of(5, 6, null, true, "Message for null valid[5,6] value"), //
                Arguments.of(5, 6, 0, false, "Message for zero invalid[5,6] value"), //
                Arguments.of(5, 6, -1, false, "Message for negative invalid[5,6] value"), //
                Arguments.of(5, 6, 2, false, "Message for positive invalid[5,6] value"), //
                Arguments.of(5, 6, 5, true, "Message for positive valid[5,6] value") //
        );
    }

    /**
     * Test validation for range data.
     *
     * @param min      Min number range.
     * @param max      Max number range.
     * @param src      Source schema.
     * @param expected Expected validation result.
     * @param message  Failed test message.
     */
    @ParameterizedTest
    @MethodSource("hasRangeSourceData")
    void schemaHasRange(Integer min, Integer max, Integer src, Boolean expected, String message) {
        if (min != null || max != null) {
            this.schema.range(min, max);
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }
}
