package com.example.s26g5;

public class SessionManager {
    private static SessionManager instance;
    private String uid;
    private String username;
    private boolean isAdmin;

    // Singleton Design Pattern
    public static SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void setSession(String uid, String username, boolean isAdmin) {
        this.uid = uid;
        this.username = username;
        this.isAdmin = isAdmin;
    }
    public void clearSession() {
        this.uid = null;
        this.username = null;
        this.isAdmin = false;
    }
    public boolean isLoggedIn() { return uid != null; }
    public boolean isAdmin() { return isAdmin; }
    public String getUsername() { return username; }

    public String getUid() { return uid; }
}
