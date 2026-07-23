package com.example.b07demosummer2024.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.b07demosummer2024.HomeFragment;
import com.example.b07demosummer2024.R;
import com.example.b07demosummer2024.data.FirebaseAuthActivity;

public class SignupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflator, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflator.inflate(R.layout.signup, container, false);

        Button backButton = view.findViewById(R.id.BackButton);
        FirebaseAuthActivity authManager = FirebaseAuthActivity.getFirebaseAuthInstance();
        Button signupButton = view.findViewById(R.id.SignUpButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new LoginFragment()); }
        });

        signupButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailField = view.findViewById(R.id.EmailField);
                EditText passwordField = view.findViewById(R.id.PasswordField);
                EditText usernameField = view.findViewById(R.id.UsernameField);
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();
                String username = usernameField.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                    Toast.makeText(
                                    getContext(),
                                    "Enter email, username and password",
                                    Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                authManager.signupUser(email, password, username);
                loadFragment(new HomeFragment());
            }
        }));



        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
