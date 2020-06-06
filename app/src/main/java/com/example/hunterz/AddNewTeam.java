package com.example.hunterz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class AddNewTeam extends Fragment {

    private TextView cricketImageBtn,footballImageBtn,volleyballImageBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_new_team, container, false);

        cricketImageBtn = view.findViewById(R.id.cricket_title);
        footballImageBtn = view.findViewById(R.id.football_title);
        volleyballImageBtn = view.findViewById(R.id.volleyball_title);


        // Click On Cricket Image
        cricketImageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Fragment cricketTeam = new CricketTeam();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout_team, cricketTeam );
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Click On Football Image
        footballImageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        // Click On Volleyball Image
        volleyballImageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    public void openRegister(Class fragment)
    {
        Intent intent = new Intent(getContext(),fragment);
        startActivity(intent);
    }

}
