package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        this.put("required", (data) -> data != null && data != 0);

        return this;
    }

    public NumberSchema positive() {
        this.required();
        this.put("positive", (data) -> data > 0);

        return this;
    }

    public NumberSchema negative() {
        this.required();
        this.put("negative", (data) -> data < 0);

        return this;
    }

    public NumberSchema range(Integer min, Integer max) {
        this.put("range", (data) -> data != null && !(min != null && data < min)
                && !(max != null && data > max));

        return this;
    }

}
