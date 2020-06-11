package com.example.hunterz;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberProfile extends Fragment {

    private ImageView myImage;
    private TextView myName,myEmail,myPhone,myNic,myAddress,myGender,myDOB,mySportType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_profile, container, false);


        myImage = view.findViewById(R.id.profile_pic);
        myName = view.findViewById(R.id.profile_name);
        myEmail = view.findViewById(R.id.profile_email);
        myPhone = view.findViewById(R.id.member_phone);
        myNic = view.findViewById(R.id.member_nic);
        myAddress = view.findViewById(R.id.member_address);
        myGender = view.findViewById(R.id.member_gender);
        myDOB = view.findViewById(R.id.member_dateOfBirth);
        mySportType = view.findViewById(R.id.member_sport);

        viewMyProfile();

        return view;
    }

    public void viewMyProfile()
    {
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cursor res = db.getMember("SELECT * FROM member_Table,authentication_Table WHERE member_Table.authentication_id = authentication_Table.authentication_id AND member_Table.member_id='"+SignIn.MemberID+"'");

        if(res.moveToFirst())
        {
            byte[] image = res.getBlob(9);
            Bitmap bmp = BitmapFactory.decodeByteArray(image,0,image.length);

            myImage.setImageBitmap(bmp);
            myName.setText(res.getString(1));
            myEmail.setText(res.getString(13));
            myPhone.setText(res.getString(4));
            myNic.setText(res.getString(5));
            myAddress.setText(res.getString(2));
            myGender.setText(res.getString(7));
            myDOB.setText(res.getString(3));
            mySportType.setText(res.getString(6));
        }


    }

}
