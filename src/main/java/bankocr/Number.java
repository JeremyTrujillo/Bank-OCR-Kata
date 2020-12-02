package bankocr;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Number {
    private final String[] strings;

    public Number(String[] strings) {
        this.strings = strings;
    }
}
