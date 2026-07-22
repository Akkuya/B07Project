package com.example.s26g5;

import java.util.Hashtable;

public class Artifact {
    private Hashtable<String, ArtifactField> fields = new Hashtable<>();

    public Artifact(){}

    public void addField(ArtifactField field){
        fields.put(field.getName(), field);
    }

    public ArtifactField getField(String key){
        return fields.get(key);
    }
}
