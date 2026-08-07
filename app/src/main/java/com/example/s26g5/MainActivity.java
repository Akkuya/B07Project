package com.example.s26g5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//import com.example.s26g5.data.FirebaseDBManager;
import com.example.s26g5.user.LoginFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.s26g5.data.FirebaseAuthManager;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    private DrawerLayout drawerLayout;
    private View headerLayout;
    private View sidebarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        headerLayout = findViewById(R.id.headerLayout);
        sidebarLayout = findViewById(R.id.sidebarLayout);

        setNavigationVisible(false);

        View buttonOpenDrawer = findViewById(R.id.buttonOpenDrawer);
        View menuBrowse = findViewById(R.id.menuBrowse);
        View menuSavedArtifacts = findViewById(R.id.menuSavedArtifacts);
        View menuWebsite = findViewById(R.id.menuWebsite);
        View menuLogout = findViewById(R.id.menuLogout);
        View menuAddAdmin = findViewById(R.id.menuAddAdmin);
        View buttonLogo = findViewById(R.id.headerLogo);
        // Temporary testing value:
        // true = admin
        // false = normal user
        boolean isStaff = false;

        if (isStaff) {
            menuAddAdmin.setVisibility(View.VISIBLE);
        } else {
            menuAddAdmin.setVisibility(View.GONE);
        }

        menuAddAdmin.setOnClickListener(v -> {
            Toast.makeText(
                    this,
                    "Add Admin page has not been created yet",
                    Toast.LENGTH_SHORT
            ).show();

            closeDrawer();
        });
        buttonLogo.setOnClickListener(v -> {
            showHomePage();
            closeDrawer();
        });
        menuAddAdmin.setOnClickListener(v -> {
            Toast.makeText(this, "Add Admin page has not been created yet", Toast.LENGTH_SHORT).show();
            closeDrawer();
        });
        buttonOpenDrawer.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );
        menuBrowse.setOnClickListener(v -> {
            loadFragment(new RecyclerViewFragment());
            closeDrawer();
        });
        menuSavedArtifacts.setOnClickListener(v -> {
            loadFragment(new SavedArtifactsFragment());
            closeDrawer();
        });
        menuWebsite.setOnClickListener(v -> {
            openWebsite("https://www.taam.ca/index.php/en/");
            closeDrawer();
        });
        menuLogout.setOnClickListener(v -> {
            FirebaseAuthManager.getFirebaseAuthInstance().logoutUser();

            setNavigationVisible(false);
            showLoginPage();
        });

        db = FirebaseDatabase.getInstance("https://b07-demo-summer-2024-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = db.getReference("testDemo");

//        myRef.setValue("B07 Demo!");
        myRef.child("movies").setValue("B07 Demo!");

        if (savedInstanceState == null) {
            loadFragment(new LoginFragment());
        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void setNavigationVisible(boolean visible) {
        if (visible) {
            headerLayout.setVisibility(View.VISIBLE);
            sidebarLayout.setVisibility(View.VISIBLE);

            drawerLayout.setDrawerLockMode(
                    DrawerLayout.LOCK_MODE_UNLOCKED
            );
        } else {
            drawerLayout.closeDrawer(GravityCompat.START);

            headerLayout.setVisibility(View.GONE);
            sidebarLayout.setVisibility(View.GONE);

            drawerLayout.setDrawerLockMode(
                    DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            );
        }
    }
    private void openWebsite(String websiteUrl) {
        Intent browserIntent = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse(websiteUrl)
        );

        try {
            startActivity(browserIntent);
        } catch (ActivityNotFoundException exception) {
            Toast.makeText(
                    this,
                    "No browser is available",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    private void showLoginPage() {
        getSupportFragmentManager().popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
        );

        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.fragment_container,
                        new LoginFragment()
                )
                .commit();
    }
    private void showHomePage() {
        getSupportFragmentManager().popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
        );
        getSupportFragmentManager()
                .beginTransaction()
                .replace(
                        R.id.fragment_container,
                        new HomeFragment()
                )
                .commit();

        setNavigationVisible(true);
    }

    public void navigateToSavedArtifactsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SavedArtifactsFragment())
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}