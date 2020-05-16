package com.example.hunterz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ViewMember extends Fragment {

    ViewPager viewPager;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_member, container, false);

//        ArrayList<Member> member = new ArrayList<>();
//        member.add(new Member("Ramzan Lafir","ramzanlafir@yahoo.com","0771234321","HUN001","912345678V","Activate",R.drawable.hunterz_logo));
//        member.add(new Member("Manoj Dilhan","manojdilhan@yahoo.com","0729876134","HUN002","562745578V","Deactivate",R.drawable.hunterz_logo));
//        member.add(new Member("Rusni Rasik","rusniradik@yahoo.com","0759611134","HUN003","869735128V","Activate",R.drawable.hunterz_logo));
//
//        recyclerView=view.findViewById(R.id.memberView);
//        RecycleViewerMember adapterMenu = new RecycleViewerMember(member);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
//        recyclerView.setAdapter(adapterMenu);

        return view;
    }


}
