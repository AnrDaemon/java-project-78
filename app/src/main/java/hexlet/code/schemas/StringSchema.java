package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        this.put("required", (data) -> data != null && !data.isEmpty());

        return this;
    }

    public StringSchema minLength(int length) {
        this.put("minLength", (data) -> data != null && !(data.length() < length));

        return this;
    }

    public StringSchema maxLength(int length) {
        this.put("maxLength", (data) -> data != null && !(data.length() > length));

        return this;
    }

    public StringSchema contains(String str) {
        this.put("contains", (data) -> data != null && data.contains(str));

        return this;
    }

}
