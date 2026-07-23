package com.example.s26g5.data;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;


public final class FirebaseAuthManager {
    private final FirebaseAuth authManager = FirebaseAuth.getInstance();
    private static FirebaseAuthManager FirebaseAuthInstance;

    private FirebaseAuthManager() { }

    public static FirebaseAuthManager getFirebaseAuthInstance() {
        if (FirebaseAuthInstance == null) { FirebaseAuthInstance = new FirebaseAuthManager(); }
        return FirebaseAuthInstance;
    }

    public FirebaseUser getUserInfo() {
        //add more fields accordingly
        // pull username
        return authManager.getCurrentUser();
    }

    public void signupUser(String email, String password, String username) {
        authManager.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userUID = getUserInfo().getUid();
                                addUsername(userUID, email, username);
                                // startSession(getUserInfo());
                                Log.d("Signup", "Success creating account");
                            } else {
                                Log.w("Signup", "Failure creating account", task.getException());
                            }
                        }
                    });
    }

    private void addUsername(String userUID, String email, String username) {
        String path = "users/"+userUID;
        Log.d("DEBUGGING", path);
        HashMap<String, String> user = new HashMap<String, String>();
        user.put("email", email);
        user.put("username", username);

        FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();
        boolean success = db.insertInfo(path, user);

        if (success) Log.d("Signup", "Successful in attaching username");
    }

    public void loginUser(String email, String password) {
        authManager.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // startSession(getUserInfo());
                                Log.d("Login",
                                      "Success logging in");
                            } else {
                                Log.w("Login",
                                      "Failure logging in",
                                      task.getException());
                            }
                        }
                    });
    }

    public void logoutUser() {
        authManager.signOut();
        if (getUserInfo() == null) {
            Log.d("Logout", "User has logged out");
        }
        else {
            Log.w("Logout", "Error logging out");
        }
    }
}
