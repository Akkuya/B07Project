package com.example.s26g5.user;


import com.example.s26g5.HomeFragment;
import com.example.s26g5.data.FirebaseAuthManager;

public class SignupPresenter implements UICallbackInterface {
    private final SignupFragment view;
    private final FirebaseAuthManager model;

    public SignupPresenter(SignupFragment view, FirebaseAuthManager model) {
        this.view = view;
        this.model = model;
    }

    public String checkCreds(String username, String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return "Email and password must not be empty";
        }
        if (password.length() < 6) {
            return "Length of password must be greater than 5";
        }
        return null;
    }

    public void signup(String username, String email, String password){
        model.signupUser(email, password, username, "visitor", SignupPresenter.this);
    }

    @Override
    public void onSuccess(Object result) { view.loadFragment(new HomeFragment()); }

    @Override
    public void onFailure(Object result) { view.makeToast("Error creating user"); }
}
