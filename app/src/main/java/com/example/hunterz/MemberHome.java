package com.example.hunterz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MemberHome extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home);

        // Bottom Navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.news_feed);

        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_member_home,new NewsFeed()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;

                switch (menuItem.getItemId())
                {
                    case R.id.member_home:
                        fragment = new NewsFeed();
                        break;

                    case R.id.member_profile:
                        fragment = new MemberProfile();
                        break;

                    case R.id.member_team:
                        fragment = new ViewTeam();
                        break;

                    case R.id.member_account:
                        fragment = new MemberAccount();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_member_home,fragment).commit();
                return true;
            }
        });


    }



}
