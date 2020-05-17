package com.example.hunterz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMember extends Fragment {

    ViewPager viewPager;
    RecyclerView recyclerView;
    ArrayList<Member> member;
    DatabaseReference databaseReference;
    RecycleViewerMember adapterMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_member, container, false);

        member = new ArrayList<Member>();
        recyclerView=view.findViewById(R.id.memberView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        databaseReference = FirebaseDatabase.getInstance().getReference("Member");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<Member> list = new ArrayList<>();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Member user=postSnapshot.getValue(Member.class);
                    list.add(user);
                }

                adapterMenu = new RecycleViewerMember(list);
                recyclerView.setAdapter(adapterMenu);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read user", error.toException());
            }
        });

        return view;
    }


}
