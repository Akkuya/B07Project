package com.example.s26g5.user;

public final class SessionManager {

    private boolean isAdmin;
    private boolean isLoggedIn;
    public User user;

    private static SessionManager instance;
    private SessionManager() { };
    public static SessionManager getSessionInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void startSession(User userIns) {
        user = userIns;
        isAdmin = user.getRole().equals("admin");
        isLoggedIn = true;
    }

    public void clearSession() {
        user = null;
        isLoggedIn = false;
        isAdmin = false;
    }

    public boolean isLoggedIn() { return isLoggedIn; }
    public boolean isAdmin() { return isAdmin; }

}