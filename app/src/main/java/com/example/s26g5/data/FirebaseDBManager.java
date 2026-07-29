package com.example.s26g5.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class FirebaseDBManager {
    private final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private static FirebaseDBManager FirebaseDBInstance;
    private FirebaseDBManager() { }

    public static FirebaseDBManager getFirebaseDBInstance() {
        if (FirebaseDBInstance == null) { FirebaseDBInstance = new FirebaseDBManager(); }
        return FirebaseDBInstance;
    }

    public Object getInfo(String path) {
        Object result = null;
        db.child(path).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
//                    result = task.getResult().getValue();
//                    System.out.println("DEBUGGING " + task.getResult().getValue());
                }
                else {
                    Log.d("Retrieve Info", "Error fetching data");
                }
            }
        });
        return result;
    }

    public boolean insertInfo(String path, Object item) {
        db.child(path).setValue(item);
        return true;
    }

    public boolean updateItem(String path, HashMap<String, Object> item) {
        db.child(path).updateChildren(item);
        return true;
    }

    public boolean deleteItem(String path, Integer LotNumber) {
        // TODO: Implement ItemDelete eventually
        return true;
    }

    public boolean deleteUserData(String uid) {
        db.child("users").child(uid).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Delete User", "User removed successfully");
                        } else {
                            Log.d("Delete User", "Error removing user: " + task.getException());
                        }
                    }
                });
        return true;
    }
}
