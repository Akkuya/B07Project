package com.example.s26g5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.s26g5.item_viewing.ItemDetails;
import com.example.s26g5.item_viewing.SavedArtifactsFragment;
import com.example.s26g5.user.LoginFragment;
import com.example.s26g5.user.SessionManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;
import android.util.Log;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import com.example.s26g5.item_viewing.ArtifactBrowserFragment;


import com.example.s26g5.data.FirebaseAuthManager;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    private DrawerLayout drawerLayout;
    private View headerLayout;
    private View sidebarLayout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = SessionManager.getSessionInstance();

        drawerLayout = findViewById(R.id.drawerLayout);
        headerLayout = findViewById(R.id.headerLayout);
        sidebarLayout = findViewById(R.id.sidebarLayout);

        setNavigationVisible(false);

        View buttonOpenDrawer = findViewById(R.id.buttonOpenDrawer);
        View menuBrowse = findViewById(R.id.menuBrowse);
        View menuSavedArtifacts = findViewById(R.id.menuSavedArtifacts);
        View menuWebsite = findViewById(R.id.menuWebsite);
        View menuLogout = findViewById(R.id.menuLogout);
        View menuAdminDashboard = findViewById(R.id.menuAdminDashboard);
        View buttonLogo = findViewById(R.id.headerLogo);
        View menuProfileSetting = findViewById(R.id.menuProfileSetting);
        // Temporary testing value:
        // true = admin
        // false = normal user
        boolean isStaff = sessionManager.isAdmin();

        Log.d("MainActivity", "isStaff = " + isStaff);

        if (isStaff) {
            menuAddAdmin.setVisibility(View.VISIBLE);
        } else {
            menuAddAdmin.setVisibility(View.GONE);
        }

        buttonLogo.setOnClickListener(v -> {
            showHomePage();
            closeDrawer();
        });
        menuAdminDashboard.setOnClickListener(v -> {
            // navigateToFragment();
        });
        buttonOpenDrawer.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START)
        );
        menuBrowse.setOnClickListener(v -> {
            navigateToFragment(new ArtifactBrowserFragment());
        });
        menuSavedArtifacts.setOnClickListener(v -> {
            navigateToFragment(new SavedArtifactsFragment());
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
        menuProfileSetting.setOnClickListener(v -> {
            navigateToFragment(new SettingsFragment());
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

    private void navigateToFragment(Fragment destinationFragment) {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);

        if (currentFragment != null
                && currentFragment.getClass().equals(destinationFragment.getClass())) {
            closeDrawer();
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, destinationFragment)
                .addToBackStack(destinationFragment.getClass().getSimpleName())
                .setReorderingAllowed(true)
                .commit();

        closeDrawer();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer();
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}


//{
// image=https://stnlsqpruhjfocqldrmv.supabase.co/storage/v1/object/public/artifact%20image/artifacts/SONG-BOWLS-537A82/134831.png,
// notes=N/A,
// acquisitionMethod=donation,
// description=This tea bowl has a flared mouth, deep curved walls, and a short ring foot. The outer wall is carved with protruding large lotus petal motifs. The interior of the bowl has a ring-shaped unglazed area at the bottom, with a central lotus flower pattern. The tea bowl features a green-yellow glaze, which is thick in texture, and the entire piece is covered with fine crackle patterns.,
// lotNumber=SONG-BOWLS-537A82,
// accessionNumber=673,
// currentLocation=taam-hall-d,
// conditionReport=good,
// provenance=china,
// culturalOrigin=chinese,
// dynasty=Song,
// materials=porcelain,
// artifactName=A 'Ding' Lotus Petal Pattern Tea Bowl,
// Song Dynasty,
// category=ancient-porcelain,
// dimensions=3 × 6 inches,
// likes=[FqgPyexXSvWooGMfmxMpKjOyD2M2]
// }