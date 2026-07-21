package com.example.s26g5;

public abstract class ArtifactField {
    private String name;
    private boolean mandatory;

    public ArtifactField(String name, boolean mandatory, String value){
        this.name = name;
        this.mandatory = mandatory;
        setValueFromString(value);
    }

    public String getName(){
        return name;
    }

    public boolean getMandatory(){
        return mandatory;
    }

    public abstract String getValue();

    public abstract void setValueFromString(String value);
}
