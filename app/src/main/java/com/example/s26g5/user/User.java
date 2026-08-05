package com.example.s26g5.user;

import java.util.HashMap;
import java.util.Objects;

public class User {
    private String UID;
    private String username;
    private String email;
    private String role;
    private String[] saved_artifacts;

    public User(HashMap<String, Object> userInfo) {
        UID = Objects.requireNonNull(userInfo.get(UID)).toString();
        username = Objects.requireNonNull(userInfo.get(username)).toString();
        email = Objects.requireNonNull(userInfo.get(email)).toString();
        role = Objects.requireNonNull(userInfo.get(role)).toString();
        saved_artifacts = (String[]) userInfo.get(saved_artifacts);
    }

    public String getUid() { return UID; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String[] getSavedItems() { return saved_artifacts; }

    public boolean commentOnItem() { return false; }
}
