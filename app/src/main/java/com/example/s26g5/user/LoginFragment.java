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

public class LoginFragment extends Fragment {
    EditText emailField;
    EditText passwordField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflator, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflator.inflate(R.layout.login, container, false);
        ((MainActivity) requireActivity()).setNavigationVisible(false);

        FirebaseAuthManager authManager = FirebaseAuthManager.getFirebaseAuthInstance();
        LoginPresenter presenter = new LoginPresenter(this, authManager);

        Button signupPromptButton = view.findViewById(R.id.signupPromptButton);
        Button loginButton = view.findViewById(R.id.SignUpButton);

        signupPromptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loadFragment(new SignupFragment()); }
        });

        loginButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailField = view.findViewById(R.id.EmailField);
                passwordField = view.findViewById(R.id.PasswordField);
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                String message = presenter.checkCreds(email, password);
                if (message != null)
                    makeToast(message);
                else
                    presenter.login(email, password);

                emailField.setText("");
                passwordField.setText("");
            }
        }));

        return view;
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
