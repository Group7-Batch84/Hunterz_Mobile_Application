package com.example.hunterz.ui.manage_team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hunterz.AddMember;
import com.example.hunterz.R;
import com.example.hunterz.SearchMember;
import com.example.hunterz.ViewMember;
import com.example.hunterz.ui.send_notification.SendNotificationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManageTeamFragment  extends Fragment {

    private SendNotificationViewModel sendNotificationViewModel;
    BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendNotificationViewModel = ViewModelProviders.of(this).get(SendNotificationViewModel.class);

        View view = inflater.inflate(R.layout.fragment_manage_team, container, false);

//        final TextView textView = view.findViewById(R.id.text_slideshow);
//        sendNotificationViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

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
