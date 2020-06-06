package com.example.hunterz.ui.manage_team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hunterz.AddNewTeam;
import com.example.hunterz.NewMember;
import com.example.hunterz.R;
import com.example.hunterz.StatusMember;
import com.example.hunterz.ViewTeam;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManageTeamFragment extends Fragment {

    private ManageTeamViewModel manageTeamViewModel;
    BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        manageTeamViewModel = ViewModelProviders.of(this).get(ManageTeamViewModel.class);

        View view = inflater.inflate(R.layout.fragment_manage_team, container, false);


        // Bottom Navigation
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.add_new_team);

        if(savedInstanceState == null)
        {
            getFragmentManager().beginTransaction().replace(R.id.frameLayout_team,new AddNewTeam()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;

                switch (menuItem.getItemId())
                {
                    case R.id.add_new_team:
                        fragment = new AddNewTeam();
                        break;

                    case R.id.view_team:
                        fragment = new ViewTeam();
                        break;
                }
                getFragmentManager().beginTransaction().replace(R.id.frameLayout_team,fragment).commit();
                return true;
            }
        });


        return view;
    }
}