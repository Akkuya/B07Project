package com.example.s26g5.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.s26g5.user.UICallbackInterface;
import com.example.s26g5.user.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;


public final class FirebaseAuthManager {
    private final FirebaseAuth authManager = FirebaseAuth.getInstance();
    private final SessionManager sessionInstance = SessionManager.getSessionInstance();
    private final FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();

    private static FirebaseAuthManager FirebaseAuthInstance;
    private FirebaseAuthManager() { }
    public static FirebaseAuthManager getFirebaseAuthInstance() {
        if (FirebaseAuthInstance == null) { FirebaseAuthInstance = new FirebaseAuthManager(); }
        return FirebaseAuthInstance;
    }

    private void addUser(String userUID, String email, String username) {
        String path = "users/"+userUID;
        HashMap<String, String> user = new HashMap<String, String>();
        user.put("email", email);
        user.put("username", username);
        user.put("role", "visitor");
        user.put("saved_artifacts", null);


        FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();
        boolean success = db.insertInfo(path, user);

        if (success) Log.d("Signup", "Successful in attaching username");
    }

    private void getUserInfo(String userUID) {

    }

    public void signupUser(String email, String password, String username, UICallbackInterface callback) {
        authManager.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                addUser(authManager.getUid(), email, username);
                                callback.onSuccess();
                                // startSession(getUserInfo());
                                Log.d("Signup", "Success creating account");
                            } else {
                                callback.onFailure();
                                Log.w("Signup", "Failure creating account", task.getException());
                            }
                        }
                    });
    }

    public void loginUser(String email, String password, UICallbackInterface callback) {
        authManager.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // create session, call UI callback, Log Success
                                callback.onSuccess();
                                Log.d("Login", "Success logging in");
                            } else {
                                // call UI callback, Log Failure
                                callback.onFailure();
                                Log.w("Login", "Failure logging in", task.getException());
                            }
                        }
                    });
    }

    public void logoutUser() {
        sessionInstance.clearSession();
        if (!sessionInstance.isLoggedIn()) {
            authManager.signOut();
            Log.d("Logout", "User has logged out");
        }
        else {
            Log.w("Logout", "Error logging out");
        }
    }

}
