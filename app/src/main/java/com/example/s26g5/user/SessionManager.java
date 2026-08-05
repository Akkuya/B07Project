package com.example.s26g5.user;

import android.util.Log;

import com.example.s26g5.data.FirebaseDBManager;
import com.google.firebase.database.DataSnapshot;

public class SessionManager implements  UICallbackInterface{
    private static SessionManager instance;
    private final FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();

    private String UID;
    private String username;
    private String email;
    private String role;
    private String[] saved_artifacts;
    private boolean isAdmin;

    // Singleton Design Pattern
    public static SessionManager getSessionInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void setSession(String uid) {
        this.UID = uid;
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
    public String[] getSavedItems() { return saved_artifacts; }

    @Override
    public void onSuccess(Object result) {
        DataSnapshot json = (DataSnapshot) result;
        for (DataSnapshot child : json.getChildren()) {
            Object value = child.getValue();

            Log.d(
                    "SESSION",
                    child.getKey() + " = " + value
            );
        }
    }

    @Override
    public void onFailure(Object result) {

    }
}