package com.example.s26g5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.s26g5.data.FirebaseAuthManager;
import com.example.s26g5.data.FirebaseDBManager;
import com.example.s26g5.user.LoginFragment;
import com.example.s26g5.user.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accountinfo_layout, container, false);

        view.findViewById(R.id.buttonBack).setOnClickListener(v ->
                getParentFragmentManager().popBackStack());

        view.findViewById(R.id.btnDeleteAccount).setOnClickListener(v -> {
            SessionManager sm = SessionManager.getSessionInstance();
            FirebaseAuthManager auth = FirebaseAuthManager.getFirebaseAuthInstance();
            final String uid;

            if (sm.isLoggedIn()) {
                Toast.makeText(getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
                return;
            }

            uid = sm.getUid();

            FirebaseDBManager db = FirebaseDBManager.getFirebaseDBInstance();
            db.deleteUserData(uid)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getContext(),
                                        "Failed to delete data: " + task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                                return;
                            }

                            auth.getUserInfo().delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> deleteTask) {
                                            if (deleteTask.isSuccessful()) {
                                                sm.clearSession();
                                                Toast.makeText(getContext(), "Account deleted", Toast.LENGTH_SHORT).show();

                                                getParentFragmentManager().popBackStack(null, 0);
                                                getParentFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.fragment_container, new LoginFragment())
                                                        .commit();
                                            } else {
                                                Toast.makeText(getContext(),
                                                        "Failed to delete account: " + deleteTask.getException().getMessage(),
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    });
        });

        return view;
    }
}