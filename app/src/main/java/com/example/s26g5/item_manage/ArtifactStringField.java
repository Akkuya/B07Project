package com.example.s26g5.item_manage;

public class ArtifactStringField extends ArtifactField<String> {
    private String value;

    public ArtifactStringField(String name, boolean mandatory, String value){
        super(name, mandatory);
        this.value = value;
    }

    @Override
    public String getValue(){
        return value;
    }
}
