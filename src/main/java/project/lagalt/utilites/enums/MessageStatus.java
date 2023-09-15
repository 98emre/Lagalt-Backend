package project.lagalt.utilites.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MessageStatus {

    UNREAD,READ;

    @JsonValue
    public String toValue(){
        return this.name();
    }

    @JsonCreator
    public Category forValue(String value){
        return Category.valueOf(value.toUpperCase());
    }
}
