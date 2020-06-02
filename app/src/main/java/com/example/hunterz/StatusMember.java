package com.example.hunterz;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StatusMember extends Fragment {

    ViewPager viewPager;
    RecyclerView recyclerView;
    ArrayList<Member> member;
    RecycleViewerMember adapterMenu;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_member, container, false);

        member = new ArrayList<Member>();
        recyclerView=view.findViewById(R.id.memberStatus);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        progressBar=view.findViewById(R.id.progressBar1);

        viewNewMember();

        return view;
    }


    public void viewNewMember() {
        ArrayList<Member> member = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cursor res = db.getMember("SELECT * FROM member_Table,authentication_Table WHERE member_Table.authentication_id = authentication_Table.authentication_id");

        while (res.moveToNext()) {
            byte[] image = res.getBlob(9);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

            member.add(new Member(res.getString(0),res.getString(1),res.getString(4),res.getString(13),
                    res.getString(5),res.getString(7),res.getString(2),res.getString(3),
                    res.getString(6), res.getString(14),res.getString(8),bmp));
        }
        res.close();

        adapterMenu = new RecycleViewerMember(member,"status",getContext());
        recyclerView.setAdapter(adapterMenu);
        progressBar.setVisibility(View.GONE);

    }

}
