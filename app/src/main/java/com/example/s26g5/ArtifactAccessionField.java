package com.example.s26g5;

public class ArtifactAccessionField extends ArtifactField{
    private int year;
    private int group;
    private int object;

    public ArtifactAccessionField(String name, boolean mandatory, String value){
        super(name, mandatory, value);
    }

    @Override
    public String getValue(){
        return year + "." + group + "." + object;
    }

    @Override
    public void setValueFromString(String value){
        String[] split = value.split("\\.");
        year = Integer.parseInt(split[0]);
        group = Integer.parseInt(split[1]);
        object = Integer.parseInt(split[2]);
    }
}
