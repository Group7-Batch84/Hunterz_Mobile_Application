package com.example.hunterz;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ViewTeam extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_team, container, false);

        progressBar=view.findViewById(R.id.progressBar1);
        recyclerView=view.findViewById(R.id.teamView);

        // To View all the Team
        viewTeams("cricket");
        viewTeams("football");
        viewTeams("volleyball");

        return view;
    }

    public void viewTeams(String sportType)
    {
        // Menu List
        ArrayList<Team> team = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cursor res = null;

        if(sportType.equals("cricket"))
        {
            res = db.getTeam("SELECT * FROM cricket_Table");
        }
        else if(sportType.equals("football"))
        {
            res = db.getTeam("SELECT * FROM football_Table");
        }
        else if(sportType.equals("volleyball"))
        {
            res = db.getTeam("SELECT * FROM volleyball_Table");
        }

        while (res.moveToNext())
        {
            if(sportType.equals("cricket"))
            {
                team.add(new Team(res.getString(0), res.getString(1), res.getString(2), res.getString(3),
                        res.getString(4), res.getString(5), res.getString(6), res.getString(7),
                        res.getString(8), res.getString(9), res.getString(10), res.getString(11),
                        res.getString(12), res.getString(13), res.getString(14), res.getString(15),
                        res.getString(16)));
            }
            else if(sportType.equals("football"))
            {
                team.add(new Team(res.getString(0), res.getString(1), res.getString(2), res.getString(3),
                        res.getString(4), res.getString(5), res.getString(6), res.getString(7),
                        res.getString(8), res.getString(9), res.getString(10), res.getString(11),
                        res.getString(12), res.getString(13), res.getString(14), res.getString(15),
                        res.getString(16)));
            }
            else if(sportType.equals("volleyball"))
            {
                team.add(new Team(res.getString(0), res.getString(1), res.getString(2), res.getString(3),
                        res.getString(4), res.getString(5), res.getString(6), res.getString(7),
                        res.getString(8), res.getString(9), res.getString(10), res.getString(11),
                        res.getString(12), res.getString(13)));
            }

        }
        res.close();
        // Menu List View
        RecycleViewerTeam adapterTeam = new RecycleViewerTeam(team,getContext(),sportType);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(adapterTeam);

        progressBar.setVisibility(View.GONE);
    }

}
