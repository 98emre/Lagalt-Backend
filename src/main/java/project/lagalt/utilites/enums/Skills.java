package project.lagalt.utilites.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Skills {
    JAVA, JAVASCRIPT, REACT, ANGULAR, C;

    @JsonValue
    public String toValue(){
        return this.name();
    }

    @JsonCreator
    public static Skills forValue(String value){
        return Skills.valueOf(value.toUpperCase());
    }
}
