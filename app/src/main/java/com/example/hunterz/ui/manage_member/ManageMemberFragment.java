package com.example.hunterz.ui.manage_member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hunterz.AddMember;
import com.example.hunterz.R;
import com.example.hunterz.SearchMember;
import com.example.hunterz.ViewMember;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManageMemberFragment extends Fragment {

    private ManageMemberViewModel manageMemberViewModel;
    BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        manageMemberViewModel = ViewModelProviders.of(this).get(ManageMemberViewModel.class);
        View view = inflater.inflate(R.layout.fragment_manage_member, container, false);


        // Bottom Navigation
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.add_member);

        if(savedInstanceState == null)
        {
            getFragmentManager().beginTransaction().replace(R.id.frameLayout_member,new AddMember()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;

                switch (menuItem.getItemId())
                {
                    case R.id.add_member:
                        fragment = new AddMember();
                        break;

                    case R.id.view_member:
                        fragment = new ViewMember();
                        break;

                    case R.id.search_member:
                        fragment = new SearchMember();
                        break;
                }
                getFragmentManager().beginTransaction().replace(R.id.frameLayout_member,fragment).commit();
                return true;
            }
        });


        return view;
    }
}