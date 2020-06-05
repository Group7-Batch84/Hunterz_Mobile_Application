package com.example.hunterz.ui.send_post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hunterz.PostModify;
import com.example.hunterz.PostSend;
import com.example.hunterz.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SendPostFragment extends Fragment {

    private SendPostViewModel sendPostViewModel;
    BottomNavigationView bottomNavigationPost;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendPostViewModel = ViewModelProviders.of(this).get(SendPostViewModel.class);

        View view = inflater.inflate(R.layout.fragment_send_post, container, false);

        // Bottom Navigation
        bottomNavigationPost = view.findViewById(R.id.bottom_navigation);

        //Set Home Selected
        bottomNavigationPost.setSelectedItemId(R.id.send_post);

        if(savedInstanceState == null)
        {
            getFragmentManager().beginTransaction().replace(R.id.frameLayout_post,new PostSend()).commit();
        }

        bottomNavigationPost.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment = null;

                switch (menuItem.getItemId())
                {
                    case R.id.send_post:
                        fragment = new PostSend();
                        break;

                    case R.id.delete_post:
                        fragment = new PostModify();
                        break;
                }
                getFragmentManager().beginTransaction().replace(R.id.frameLayout_post,fragment).commit();
                return true;
            }
        });


        return view;
    }
}