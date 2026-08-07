package com.example.s26g5.user;

import android.widget.Toast;

import com.example.s26g5.HomeFragment;
import com.example.s26g5.data.FirebaseAuthManager;

public class LoginPresenter implements UICallbackInterface {
    private final LoginFragment view;
    private final FirebaseAuthManager model;

    public LoginPresenter(LoginFragment view, FirebaseAuthManager model) {
        this.view = view;
        this.model = model;
    }

    public String checkCreds() {
        String email = view.getEmail();
        String password = view.getPassword();
        if (email.isEmpty() || password.isEmpty()) {
            return "Email and password must be entered";
        }
        return null;
    }

    public void login(){
        String email = view.getEmail();
        String password = view.getPassword();
        model.loginUser(email, password, LoginPresenter.this);
    }

    @Override
    public void onSuccess(Object result) { view.onSuccess(); }

    @Override
    public void onFailure(Object result) { view.onFailure(); }
}
