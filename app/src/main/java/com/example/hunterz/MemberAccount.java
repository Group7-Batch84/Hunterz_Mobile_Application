package com.example.hunterz;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MemberAccount extends Fragment {

    private LinearLayout payment,about,logout;
    private ImageView memberImage;
    Dialog myDialog;

    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_account, container, false);

        payment = view.findViewById(R.id.payment_function);
        about = view.findViewById(R.id.about_function);
        logout = view.findViewById(R.id.logout_function);
        memberImage = view.findViewById(R.id.profile_pic);

        progressBar=view.findViewById(R.id.progressBar1);
        recyclerView=view.findViewById(R.id.paymentMemberView);


        myDialog = new Dialog(getContext());

        viewMyProfile();

        // Payment On Click
        payment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Fragment myFragment = new PaymentMember();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.detailLayout, myFragment).addToBackStack(null).commit();
            }
        });

        // About On Click
        about.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myDialog.setContentView(R.layout.about);
                myDialog.show();
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


    public void viewMyProfile() {
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cursor res = db.getMember("SELECT * FROM member_Table,authentication_Table WHERE member_Table.authentication_id = authentication_Table.authentication_id AND member_Table.member_id='" + SignIn.MemberID + "'");

        if (res.moveToFirst()) {
            byte[] image = res.getBlob(9);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

            memberImage.setImageBitmap(bmp);
        }
    }



}
