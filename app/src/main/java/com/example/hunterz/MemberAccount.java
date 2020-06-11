package com.example.hunterz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MemberAccount extends Fragment {

    private LinearLayout payment,about,help,logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_account, container, false);

        payment = view.findViewById(R.id.payment_function);
        about = view.findViewById(R.id.about_function);
        help = view.findViewById(R.id.help_function);
        logout = view.findViewById(R.id.logout_function);

        // Payment On Click
        payment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        // About On Click
        about.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        // Help On Click
        help.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        // Logout On Click
        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SignIn.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }


}
