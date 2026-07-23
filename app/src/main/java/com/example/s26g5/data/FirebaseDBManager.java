package com.example.s26g5.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class FirebaseDBManager {
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private static FirebaseDBManager FirebaseDBInstance;
    private FirebaseDBManager() { }

    public static FirebaseDBManager getFirebaseDBInstance() {
        if (FirebaseDBInstance == null) { FirebaseDBInstance = new FirebaseDBManager(); }
        return FirebaseDBInstance;
    }

    public void getInfo(String path) { }

    public boolean insertInfo(String path, Object item) {
        db.child(path).setValue(item);
        return true;
    }

    public boolean updateItem(String path, Object Item) { return true; }
    public boolean deleteItem(String path, Integer LotNumber) {
        return true;
    }
}
