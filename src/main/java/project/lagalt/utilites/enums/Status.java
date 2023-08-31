package project.lagalt.utilites.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

    NOT_STARTED, IN_PROGRESS, COMPLETED;


    @JsonValue
    public String toValue(){
        return this.name();
    }

    @JsonCreator
    public Status forValue(String name){
        return Status.valueOf(name.toUpperCase());
    }
}
