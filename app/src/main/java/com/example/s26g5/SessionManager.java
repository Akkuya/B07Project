package com.example.s26g5;

public class SessionManager {
    private static SessionManager instance;
    private String uid;
    private String username;
    private boolean isAdmin;

    public static SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void setSession(String uid, String username, boolean isAdmin) {
        ;
    }
    public void clearSession() {
        ;
    }
    public boolean isLoggedIn() { return uid != null; }
    public boolean isAdmin() { return isAdmin; }
    public String getUsername() { return username; }

    public String getUid() { return uid; }
}
