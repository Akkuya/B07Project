package com.example.s26g5.data;

import android.util.Log;

import androidx.annotation.NonNull;

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

    public boolean insertInfo(String path, Object item) {
        db.child(path).setValue(item);
        return true;
    }

//    public String getInfo(String path) {
//        Object result = null;
//        db.child(path).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    result = task.getResult().getValue();
//                }
//                else {
//                    Log.d("Retrieve Info", "Error fetching data");
//                }
//            }
//        });
//        return result;
//    }

//    public boolean updateItem(String path, HashMap<String, Object> item) {
//        db.child(path).updateChildren(item);
//        return true;
//    }

//    public boolean deleteItem(String path, Integer LotNumber) {
//        return true;
//    }
}
