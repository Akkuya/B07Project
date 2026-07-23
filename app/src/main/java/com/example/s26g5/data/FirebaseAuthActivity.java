package com.example.s26g5.data;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public final class FirebaseAuthActivity {
    private final FirebaseAuth authManager = FirebaseAuth.getInstance();
    private static FirebaseAuthActivity FirebaseAuthInstance;

    private FirebaseAuthActivity() { }

    public static FirebaseAuthActivity getFirebaseAuthInstance() {
        if (FirebaseAuthInstance == null) { FirebaseAuthInstance = new FirebaseAuthActivity(); }
        return FirebaseAuthInstance;
    }

    public FirebaseUser getUserInfo() {
        return authManager.getCurrentUser();
    }

    public void signupUser(String email, String password, String username) {
        authManager.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Add and connect username
                                // startSession(getUserInfo());
                                Log.d("Signup",
                                      "Success creating account");
                            } else {
                                Log.w("Signup",
                                      "Failure creating account",
                                      task.getException());
                            }
                        }
                    });
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
