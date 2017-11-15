package net.unit8.playcatch.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.seasar.doma.Domain;

import java.util.Arrays;
import java.util.Objects;

@Domain(valueType = String.class, factoryMethod = "of")
public enum BallOwner {
    OUR("our"),
    THEIR("their");

    private final String value;

    BallOwner(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static BallOwner of(String value) {
        return Arrays.stream(BallOwner.values())
                .filter(status -> Objects.equals(status.getValue(), value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(value));
    }

}
