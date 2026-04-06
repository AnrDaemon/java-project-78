package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        this.put("required", (data) -> data != null);

        return this;
    }

    public NumberSchema notEmpty() {
        this.put("notempty", (data) -> data == null || data != 0);

        return this;
    }

    public NumberSchema positive() {
        this.put("positive", (data) -> data == null || data > 0);

        return this;
    }

    public NumberSchema negative() {
        this.put("negative", (data) -> data == null || data < 0);

        return this;
    }

    public NumberSchema range(Integer min, Integer max) {
        this.put("range", (data) -> data == null || !(min != null && data < min)
                && !(max != null && data > max));

        return this;
    }

}
