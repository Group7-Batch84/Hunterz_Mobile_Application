package com.example.hunterz;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class VolleyballTeam extends Fragment {

    private TextView[] playerName = new TextView[12];
    private EditText[] playerID = new EditText[12];
    private Button addTeamBtn;
    private TextView teamId;
    private EditText teamName;

    Validation valid = new Validation(getContext());

    String[] memberID = new String[12];
    String[] txtData = new String[12];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_volleyball_team, container, false);

        teamId = view.findViewById(R.id.team_id);
        teamName = view.findViewById(R.id.team_txt);
        addTeamBtn = view.findViewById(R.id.add_team_btn);

        playerID[0] = view.findViewById(R.id.captain_txt);
        playerName[0] = view.findViewById(R.id.captain_name);
        playerID[1] = view.findViewById(R.id.player2_txt);
        playerName[1] = view.findViewById(R.id.player2_name);
        playerID[2] = view.findViewById(R.id.player3_txt);
        playerName[2] = view.findViewById(R.id.player3_name);
        playerID[3] = view.findViewById(R.id.player4_txt);
        playerName[3] = view.findViewById(R.id.player4_name);
        playerID[4] = view.findViewById(R.id.player5_txt);
        playerName[4] = view.findViewById(R.id.player5_name);
        playerID[5] = view.findViewById(R.id.player6_txt);
        playerName[5] = view.findViewById(R.id.player6_name);
        playerID[6] = view.findViewById(R.id.player7_txt);
        playerName[6] = view.findViewById(R.id.player7_name);
        playerID[7] = view.findViewById(R.id.player8_txt);
        playerName[7] = view.findViewById(R.id.player8_name);
        playerID[8] = view.findViewById(R.id.player9_txt);
        playerName[8] = view.findViewById(R.id.player9_name);
        playerID[9] = view.findViewById(R.id.player10_txt);
        playerName[9] = view.findViewById(R.id.player10_name);
        playerID[10] = view.findViewById(R.id.player11_txt);
        playerName[10] = view.findViewById(R.id.player11_name);
        playerID[11] = view.findViewById(R.id.player12_txt);
        playerName[11] = view.findViewById(R.id.player12_name);

        teamId.setText(generateID("VTM","select team_id from volleyball_Table")); // Set the team Id

        addTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTeam();
            }
        });


        return view;
    }


    public void addTeam()
    {
        int count = 0 ;
        boolean res = false;
        String teamID = "";
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());

        getTextData(); // pass all the edit text value to array

        String getTamName = valid.emptyField(teamName,getString(R.string.teamName_errorMessage1));

        teamID = generateID("FTM","select team_id from football_Table");

        memberID[0] = valid.memberIdCheck(playerID[0],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[0].getText().toString().toUpperCase()),playerName[0]);
        memberID[1] = valid.memberIdCheck(playerID[1],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[1].getText().toString().toUpperCase()),playerName[1]);
        memberID[2] = valid.memberIdCheck(playerID[2],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[2].getText().toString().toUpperCase()),playerName[2]);
        memberID[3] = valid.memberIdCheck(playerID[3],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[3].getText().toString().toUpperCase()),playerName[3]);
        memberID[4] = valid.memberIdCheck(playerID[4],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[4].getText().toString().toUpperCase()),playerName[4]);
        memberID[5] = valid.memberIdCheck(playerID[5],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[5].getText().toString().toUpperCase()),playerName[5]);
        memberID[6] = valid.memberIdCheck(playerID[6],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[6].getText().toString().toUpperCase()),playerName[6]);
        memberID[7] = valid.memberIdCheck(playerID[7],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[7].getText().toString().toUpperCase()),playerName[7]);
        memberID[8] = valid.memberIdCheck(playerID[8],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[8].getText().toString().toUpperCase()),playerName[8]);
        memberID[9] = valid.memberIdCheck(playerID[9],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[9].getText().toString().toUpperCase()),playerName[9]);
        memberID[10] = valid.memberIdCheck(playerID[10],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[10].getText().toString().toUpperCase()),playerName[10]);
        memberID[11] = valid.memberIdCheck(playerID[11],getString(R.string.memberId_errorMessage1),getString(R.string.memberId_errorMessage2),
                getString(R.string.memberId_errorMessage6),getString(R.string.memberId_errorMessage4),txtData,
                validMemberID(playerID[11].getText().toString().toUpperCase()),playerName[11]);


        for(int i = 0; i < memberID.length;i++)
        {
            if(memberID[i] != "")
            {
                count++;
            }
        }

        if(count == 12)
        {
            if(!getTamName.equals(""))
            {
                res = dbHandler.insertVolleyballTeam(teamID,getTamName,memberID[0],memberID[1],memberID[2],memberID[3],memberID[4],memberID[5],memberID[6],memberID[7],
                        memberID[8],memberID[9],memberID[10],memberID[11],"admin001");

                if(res == true) {
                    Toast.makeText(getContext(),"Successfully Added!", Toast.LENGTH_LONG).show();
                    clearDetails(); // Clear all input field and image view
                    teamId.setText(generateID("FTM","select team_id from football_Table")); // update the team Id after adding a team
                } else {
                    Toast.makeText(getContext(),"Error While Adding", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public String generateID(String id, String query) // Auto Generate ID
    {
        DatabaseHandler db = new DatabaseHandler(getContext());

        String IDS = "";
        try
        {
            Cursor cursor = db.getId(query);

            String idType = "";
            int count=0;

            while (cursor.moveToNext())
            {
                idType = cursor.getString(0);
                count++;
            }
            if (count > 0)
            {
                String x = idType.substring(3);
                int ID = Integer.parseInt(x);

                if (ID > 0 && ID < 9)
                {
                    ID = ID + 1;
                    IDS = id+"00" + ID;
                }
                else if (ID >= 9 && ID < 99)
                {
                    ID = ID + 1;
                    IDS = id+"0" + ID;
                }
                else if (ID >= 99)
                {
                    ID = ID + 1;
                    IDS = id + ID;
                }
            }
            else
            {
                IDS = id + "001";
            }
        }
        catch(Exception e)
        {
            Log.d("ERROR ----",e.toString());
        }
        return IDS;
    }

    public void clearDetails()
    {
        teamName.getText().clear();

        for(int i = 0; i < playerID.length; i++)
        {
            playerID[i].getText().clear();
        }

        for(int i = 0; i < playerName.length; i++)
        {
            playerName[i].setText("");
        }

    }

    public String validMemberID(String id)
    {
        DatabaseHandler db=new DatabaseHandler(getContext());
        Cursor cursor = null;
        String sport = "";
        boolean result = false;

        cursor = db.getMemberID(id);

        if(cursor.moveToFirst())
        {
            sport = cursor.getString(1);

            String[] splitedType = sport.split(" ");

            for(int i = 0 ; i < splitedType.length; i++)
            {
                if(splitedType[i].equals("Volleyball"))
                {
                    result = true;
                }
            }

            if(result)
            {
                return cursor.getString(0);
            }
            else
            {
                return "Not";
            }

        }
        return "";
    }

    public void getTextData()
    {
        txtData[0] = playerID[0].getText().toString().toUpperCase();
        txtData[1] = playerID[1].getText().toString().toUpperCase();
        txtData[2] = playerID[2].getText().toString().toUpperCase();
        txtData[3] = playerID[3].getText().toString().toUpperCase();
        txtData[4] = playerID[4].getText().toString().toUpperCase();
        txtData[5] = playerID[5].getText().toString().toUpperCase();
        txtData[6] = playerID[6].getText().toString().toUpperCase();
        txtData[7] = playerID[7].getText().toString().toUpperCase();
        txtData[8] = playerID[8].getText().toString().toUpperCase();
        txtData[9] = playerID[9].getText().toString().toUpperCase();
        txtData[10] = playerID[10].getText().toString().toUpperCase();
        txtData[11] = playerID[11].getText().toString().toUpperCase();
    }

}
