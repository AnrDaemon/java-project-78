package hexlet.code.schemas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import hexlet.code.Validator;

public class StringSchemaTest {

    private static Validator v;

    private StringSchema schema;

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
        this.schema = v.string();
    }

    /**
     * Test source data generator.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> isRequiredSourceData() {
        return Stream.of(
                // data
                Arguments.of(false, null, true, "Message for null unchecked value"), //
                Arguments.of(false, "", true, "Message for empty unchecked value"), //
                Arguments.of(false, "text", true, "Message for non-empty unchecked value"), //
                Arguments.of(true, null, false, "Message for null invalid value"), //
                Arguments.of(true, "", false, "Message for empty invalid value"), //
                Arguments.of(true, "text", true, "Message for non-empty valid value") //
        );
    }

    /**
     * Test if schema is required.
     *
     * @param flag     If a shema is required.
     * @param src      Source schema.
     * @param expected Expected validation result.
     * @param message  Failed test message.
     */
    @ParameterizedTest
    @MethodSource("isRequiredSourceData")
    void schemaIsRequired(Boolean flag, String src, Boolean expected, String message) {
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
                Arguments.of(null, null, true, "Message for null unchecked value"), //
                Arguments.of(null, "", true, "Message for empty unchecked value"), //
                Arguments.of(null, "text", true, "Message for non-empty unchecked value"), //

                // Zero minLength
                Arguments.of(0, null, false, "Message for null invalid(0) value"), // @TODO Find out if null string is
                // valid if minLength is 0
                Arguments.of(0, "", true, "Message for empty valid value"), //
                Arguments.of(0, "text", true, "Message for non-empty valid value"), //

                // Non-zero minLength
                Arguments.of(1, null, false, "Message for null invalid(1) value"), //
                Arguments.of(1, "", false, "Message for empty invalid value"), //
                Arguments.of(1, "text", true, "Message for non-empty valid(1) value") //
        );
    }

    /**
     * Test minimum schema length requirement.
     *
     * @param length   Min schema length.
     * @param src      Source schema.
     * @param expected Expected validation result.
     * @param message  Failed test message.
     */
    @ParameterizedTest
    @MethodSource("minLengthSourceData")
    void schemaHasMinLength(Integer length, String src, Boolean expected, String message) {
        if (length != null) {
            this.schema.minLength(length);
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }

    /**
     * Test source data generator.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> maxLengthSourceData() {
        return Stream.of(
                // Null maxLength
                Arguments.of(null, null, true, "Message for null unchecked value"), //
                Arguments.of(null, "", true, "Message for empty unchecked value"), //
                Arguments.of(null, "a", true, "Message for non-empty short unchecked value"), //
                Arguments.of(null, "text", true, "Message for non-empty long unchecked value"), //

                // Zero maxLength
                Arguments.of(0, null, false, "Message for null invalid(0) value"), // @TODO Find out if null string is
                // valid if maxLength is 0
                Arguments.of(0, "", true, "Message for empty valid value"), //
                Arguments.of(0, "a", false, "Message for non-empty short invalid(0) value"), //
                Arguments.of(0, "text", false, "Message for non-empty long invalid(0) value"), //

                // Non-zero maxLength
                Arguments.of(1, null, false, "Message for null invalid(1) value"), //
                Arguments.of(1, "", true, "Message for empty valid value"), //
                Arguments.of(1, "a", true, "Message for non-empty valid(1) value"), //
                Arguments.of(1, "text", false, "Message for non-empty invalid(1) value"), //

                // Bigger maxLength
                Arguments.of(2, "a", true, "Message for non-empty valid(2) value"), //
                Arguments.of(2, "text", false, "Message for non-empty invalid(2) value") //
        );
    }

    /**
     * Test minimum schema length requirement.
     *
     * @param length   Min schema length.
     * @param src      Source schema.
     * @param expected Expected validation result.
     * @param message  Failed test message.
     */
    @ParameterizedTest
    @MethodSource("maxLengthSourceData")
    void schemaHasMaxLength(Integer length, String src, Boolean expected, String message) {
        if (length != null) {
            this.schema.maxLength(length);
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }

    /**
     * Test source data generator.
     *
     * @return Test arguments.
     */
    static Stream<Arguments> containsSourceData() {
        return Stream.of(
                // Null contains
                Arguments.of(null, null, true, "Message for null unchecked value"), //
                Arguments.of(null, "", true, "Message for empty unchecked value"), //
                Arguments.of(null, "text", true, "Message for non-empty unchecked value"), //

                // Empty contains
                Arguments.of("", null, false, "Message for null invalid value"), //
                Arguments.of("", "", true, "Message for empty valid value"), //
                Arguments.of("", "text", true, "Message for non-empty valid value"), //

                // Non-zero minLength
                Arguments.of("af", null, false, "Message for null invalid(nonempty) value"), //
                Arguments.of("af", "", false, "Message for empty invalid value"), //
                Arguments.of("af", "text", false, "Message for non-empty invalid value"), //
                Arguments.of("af", "miaf", true, "Message for non-empty valid(nonempty) value") //
        );
    }

    /**
     * Test substring requirement validator.
     *
     * @param needle   Substring to search for.
     * @param src      Source schema.
     * @param expected Expected validation result.
     * @param message  Failed test message.
     */
    @ParameterizedTest
    @MethodSource("containsSourceData")
    void schemaContains(String needle, String src, Boolean expected, String message) {
        if (needle != null) {
            this.schema.contains(needle);
        }
        assertEquals(expected, this.schema.isValid(src), message);
    }
}
