package com.example.hunterz.ui.member_status;

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
import com.example.hunterz.NewMember;
import com.example.hunterz.R;
import com.example.hunterz.SearchMember;
import com.example.hunterz.StatusMember;
import com.example.hunterz.ViewMember;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MemberStatusFragment extends Fragment {

    private MemberStatusViewModel memberStatusViewModel;
    BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        memberStatusViewModel = ViewModelProviders.of(this).get(MemberStatusViewModel.class);

        View view = inflater.inflate(R.layout.fragment_member_status, container, false);


        // Bottom Navigation
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.new_member);

        if(savedInstanceState == null)
        {
            getFragmentManager().beginTransaction().replace(R.id.frameLayout_status,new NewMember()).commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;

                switch (menuItem.getItemId())
                {
                    case R.id.new_member:
                        fragment = new NewMember();
                        break;

                    case R.id.member_status:
                        fragment = new StatusMember();
                        break;
                }
                getFragmentManager().beginTransaction().replace(R.id.frameLayout_status,fragment).commit();
                return true;
            }
        });


        return view;
    }
}