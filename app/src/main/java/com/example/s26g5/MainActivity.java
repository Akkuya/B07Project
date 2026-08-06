package com.example.s26g5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.s26g5.data.FirebaseDBManager;
import com.example.s26g5.item_viewing.ItemDetails;
import com.example.s26g5.user.LoginFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance("https://cscb07s26g5-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = db.getReference("testDemo");

//        myRef.setValue("B07 Demo!");
        myRef.child("movies").setValue("B07 Demo!");

        if (savedInstanceState == null) {
            loadFragment(ItemDetails.display("SONG-BOWLS-537A82"));
//            loadFragment(new LoginFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
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