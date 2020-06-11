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
    DatabaseHandler db;
    ArrayList<Team> team;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_team, container, false);

        db = new DatabaseHandler(getContext());
        team = new ArrayList<>();
        progressBar=view.findViewById(R.id.progressBar1);
        recyclerView=view.findViewById(R.id.teamView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        // To View all the Team
        viewTeams();


        progressBar.setVisibility(View.GONE);

        return view;
    }

    public void viewTeams()
    {
        // Menu List

        Cursor resC = null;
        Cursor resF = null;
        Cursor resV = null;

            resC = db.getTeam("SELECT * FROM cricket_Table");

            resF = db.getTeam("SELECT * FROM football_Table");

            resV = db.getTeam("SELECT * FROM volleyball_Table");


        while (resC.moveToNext())
        {
                team.add(new Team(resC.getString(0), resC.getString(1), resC.getString(2), resC.getString(3),
                        resC.getString(4), resC.getString(5), resC.getString(6), resC.getString(7),
                        resC.getString(8), resC.getString(9), resC.getString(10), resC.getString(11),
                        resC.getString(12), resC.getString(13), resC.getString(14), resC.getString(15),
                        resC.getString(16)));

        }

        while(resF.moveToNext())
        {
            team.add(new Team(resF.getString(0), resF.getString(1), resF.getString(2), resF.getString(3),
                    resF.getString(4), resF.getString(5), resF.getString(6), resF.getString(7),
                    resF.getString(8), resF.getString(9), resF.getString(10), resF.getString(11),
                    resF.getString(12), resF.getString(13), resF.getString(14), resF.getString(15),
                    resF.getString(16)));

        }

        while(resV.moveToNext())
        {
            team.add(new Team(resV.getString(0), resV.getString(1), resV.getString(2), resV.getString(3),
                    resV.getString(4), resV.getString(5), resV.getString(6), resV.getString(7),
                    resV.getString(8), resV.getString(9), resV.getString(10), resV.getString(11),
                    resV.getString(12), resV.getString(13)));
        }
        resC.close();
        resF.close();
        resV.close();
        // Menu List View

        RecycleViewerTeam adapterTeamC = new RecycleViewerTeam(team,getContext(),getFragmentManager(),"team");
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(adapterTeamC);

    }

}
