package project.lagalt.utilites.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProfileVisibility {

    PUBLIC,PRIVATE;

    @JsonValue
    public String toValue(){
        return this.name();
    }

    @JsonCreator
    public Category forValue(String value){
        return Category.valueOf(value.toUpperCase());
    }
}
