package com.example.s26g5;

public class ArtifactStringField extends ArtifactField{
    private String value;

    public ArtifactStringField(String name, boolean mandatory, String value){
        super(name, mandatory, value);
    }

    @Override
    public String getValue(){
        return value;
    }

    @Override
    public void setValue(String value){
        this.value = value;
    }
}
