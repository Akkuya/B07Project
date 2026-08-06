package com.example.s26g5.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.s26g5.user.UICallbackInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public final class FirebaseDBManager {
    private final DatabaseReference db = FirebaseDatabase.getInstance("https://cscb07s26g5-default-rtdb.firebaseio.com/").getReference();
    private static FirebaseDBManager FirebaseDBInstance;
    private FirebaseDBManager() { }

    public static FirebaseDBManager getFirebaseDBInstance() {
        if (FirebaseDBInstance == null) { FirebaseDBInstance = new FirebaseDBManager(); }
        return FirebaseDBInstance;
    }

    public void getInfo(String path, UICallbackInterface callback) {
        db.child(path).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    callback.onSuccess(task.getResult());
                }
                else {
                    callback.onFailure(null);
                    Log.d("Retrieve Info", "Error fetching data");
                }
            }
        });
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

    public Task<Void> deleteUserData(String uid) {
        return db.child("users").child(uid).removeValue()
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
    }
}
