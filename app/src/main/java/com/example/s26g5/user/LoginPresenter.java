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

    public String checkCreds(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return "Email and password must be entered";
        }
        return null;
    }

    public void login(String email, String password){
        model.loginUser(email, password, LoginPresenter.this);
    }

    @Override
    public void onSuccess(Object result) { view.loadFragment(new HomeFragment()); }

    @Override
    public void onFailure(Object result) { view.makeToast("Username/password incorrect"); }
}
