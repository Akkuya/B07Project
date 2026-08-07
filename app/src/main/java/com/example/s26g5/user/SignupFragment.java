package com.example.s26g5.user;

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

import com.example.s26g5.HomeFragment;
import com.example.s26g5.R;
import com.example.s26g5.data.FirebaseAuthManager;
import com.example.s26g5.MainActivity;

public class SignupFragment extends Fragment {
    EditText emailField;
    EditText passwordField;
    EditText usernameField;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflator, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflator.inflate(R.layout.signup, container, false);
        ((MainActivity) requireActivity()).setNavigationVisible(false);


        Button backButton = view.findViewById(R.id.BackButton);
        Button signupButton = view.findViewById(R.id.SignUpButton);

        FirebaseAuthManager authManager = FirebaseAuthManager.getFirebaseAuthInstance();
        SignupPresenter presenter = new SignupPresenter(this, authManager);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new LoginFragment()); }
        });

        signupButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getEmail();
                String password = getPassword();
                String username = getUsername();

                String message = presenter.checkCreds();
                if (message != null) makeToast(message);
                else presenter.signup();
                clearFields();
            }
        }));



        return view;
    }

    public String getEmail() {
        emailField = view.findViewById(R.id.EmailField);
        return emailField.getText().toString().trim();
    }

    public String getPassword() {
        passwordField = view.findViewById(R.id.PasswordField);
        return passwordField.getText().toString().trim();
    }

    public String getUsername() {
        usernameField = view.findViewById(R.id.UsernameField);
        return usernameField.getText().toString().trim();
    }

    public void onSuccess() {
        loadFragment(new HomeFragment());
    }

    public void onFailure() {
        makeToast("Error signing up user");
    }

    public void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        usernameField.setText("");
    }

    public void makeToast(String message) {
        Toast.makeText( getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
