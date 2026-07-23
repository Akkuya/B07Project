package com.example.s26g5.data;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDBManager {
    private final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static FirebaseDBManager FirebaseDBInstance;

    private FirebaseDBManager() { }

    public static FirebaseDBManager getFirebaseDBInstance() {
        if (FirebaseDBInstance == null) { FirebaseDBInstance = new FirebaseDBManager(); }
        return FirebaseDBInstance;
    }

    public static void getInfo(String path) { }

    public static boolean insertInfo(String path, Object item) {
        return true;
    }

    public static boolean updateItem(String path, Object Item) {
        return true;
    }

    public static boolean deleteItem(String path, Integer LotNumber) {
        return true;
    }
}
