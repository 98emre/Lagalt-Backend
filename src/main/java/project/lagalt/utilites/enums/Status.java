package project.lagalt.utilites.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {

    NOT_STARTED("NOT STARTED"), IN_PROGRESS("IN PROGRESS"),COMPLETED("COMPLETED");

    private final String display;

    Status(String display){
        this.display = display;
    }


    public String getDisplay() {
        return display;
    }

    @JsonValue
    public String toValue(){
        return this.name();
    }

    @JsonCreator
    public Status forValue(String name){
        return Status.valueOf(name.toUpperCase());
    }
}
