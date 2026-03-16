package hexlet.code.schemas;

import lombok.Getter;

public final class NumberSchema {

    @Getter
    private boolean isRequired = false;

    @Getter
    private boolean isPositive = false;

    @Getter
    private boolean hasRange = false;

    @Getter
    private Integer minRange = null;

    @Getter
    private Integer maxRange = null;

    public NumberSchema required() {
        this.isRequired = true;

        return this;
    }

    public NumberSchema positive() {
        this.isPositive = true;

        return this;
    }

    public NumberSchema range(Integer min, Integer max) {
        this.hasRange = true;
        this.minRange = min;
        this.maxRange = max;

        return this;
    }

    public boolean isValid(Integer data) {
        if (this.isRequired) {
            if (data == null || data == 0) {
                return false;
            }
        }

        if (this.isPositive) {
            if (data == null || data <= 0) {
                return false;
            }
        }

        if (this.hasRange) {
            if (data == null) {
                return false;
            }
            if (this.minRange != null && data < this.minRange) {
                return false;
            }
            if (this.maxRange != null && data > this.maxRange) {
                return false;
            }
        }

        return true;
    }
}
