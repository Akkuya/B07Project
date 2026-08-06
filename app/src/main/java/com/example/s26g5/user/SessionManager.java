package com.example.s26g5.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.s26g5.data.FirebaseDBManager;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class SessionManager implements  UICallbackInterface{
    private static SessionManager instance;
    private final FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();

    private String UID;
    private String username;
    private String email;
    private String role;
    private List<String> saved_artifacts;
    private boolean isAdmin;

    // Singleton Design Pattern
    public static SessionManager getSessionInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void setSession(String uid) {
        db.getInfo("users/"+uid, SessionManager.this);
    }

    public void clearSession() {
        this.UID = null;
        this.username = null;
        this.isAdmin = false;
    }

    public boolean isLoggedIn() { return UID != null; }
    public boolean isAdmin() { return isAdmin; }

    public String getUid() { return UID; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public List<String> getSavedItems() { return saved_artifacts; }

    @Override
    public void onSuccess(Object result) {
        HashMap<String, Object> user = new HashMap<String, Object>();

        // add key-value pair
        DataSnapshot json = (DataSnapshot) result;
        for (DataSnapshot child : json.getChildren()) {
            user.put(child.getKey(), child.getValue());
        }

        //fill in rest of the fields
        UID = Objects.requireNonNull(user.get("uid")).toString();
        username = Objects.requireNonNull(user.get("username")).toString();
        email = Objects.requireNonNull(user.get("email")).toString();
        role = Objects.requireNonNull(user.get("role")).toString();
        saved_artifacts = user.get("saved_artifacts") != null
                ? (List<String>) user.get("saved_artifacts")
                : new ArrayList<>();
        isAdmin = user.get("role").equals("admin");

    }

    @Override
    public void onFailure(Object result) {
        Log.w("Session", "Failed to create session");
    }
}