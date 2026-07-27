package com.example.s26g5;

public abstract class ArtifactField<T> {
    private String name;
    private boolean mandatory;

    public ArtifactField(String name, boolean mandatory){
        this.name = name;
        this.mandatory = mandatory;
    }

    public String getName(){
        return name;
    }

    public boolean getMandatory(){
        return mandatory;
    }

    public abstract T getValue();
}
