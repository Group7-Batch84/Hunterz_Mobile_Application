package com.example.hunterz;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CricketTeam extends Fragment {

    private TextView[] playerName = new TextView[16];
    private EditText[] playerID = new EditText[16];
    private Button addTeamBtn;
    private TextView teamId;
    private EditText teamName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cricket_team, container, false);

        teamId = view.findViewById(R.id.team_id);
        teamName = view.findViewById(R.id.team_txt);
        addTeamBtn = view.findViewById(R.id.add_team_btn);

        playerID[0] = view.findViewById(R.id.captain_txt);
        playerName[0] = view.findViewById(R.id.captain_name);
        playerID[1] = view.findViewById(R.id.player12_txt);
        playerName[1] = view.findViewById(R.id.player2_name);
        playerID[2] = view.findViewById(R.id.player13_txt);
        playerName[2] = view.findViewById(R.id.player3_name);

        return view;
    }

}
