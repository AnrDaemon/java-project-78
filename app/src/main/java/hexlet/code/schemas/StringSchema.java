package hexlet.code.schemas;

import lombok.Getter;

public final class StringSchema {

    @Getter
    private boolean isRequired = false;

    @Getter
    private Integer minLength = null;

    @Getter
    private String contains = null;

    public StringSchema required() {
        this.isRequired = true;

        return this;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;

        return this;
    }

    public StringSchema contains(String str) {
        this.contains = str;

        return this;
    }

    public boolean isValid(String data) {
        if (this.isRequired) {
            if (data == null || data.isEmpty()) {
                return false;
            }
        }

        if (this.minLength != null) {
            if (data == null || data.length() < this.minLength) {
                return false;
            }
        }

        if (this.contains != null) {
            if (data == null || !data.contains(this.contains)) {
                return false;
            }
        }

        return true;
    }
}
