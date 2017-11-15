package net.unit8.playcatch.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.seasar.doma.Domain;

import java.util.Arrays;
import java.util.Objects;

@Domain(valueType = String.class, factoryMethod = "of")
public enum IssueStatus {
    UNCONTACT("UNCON", "未回答"),
    PRIMARY_REPLY("P_REP", "一時回答済み"),
    AFTER_FOLLOW("A_FOL", "フォロー中"),
    CLOSE("CLOSE", "クローズ");

    private final String value;
    private final String label;

    IssueStatus(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonCreator
    public static IssueStatus of(String value) {
        return Arrays.stream(IssueStatus.values())
                .filter(status -> Objects.equals(status.getValue(), value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(value));
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
