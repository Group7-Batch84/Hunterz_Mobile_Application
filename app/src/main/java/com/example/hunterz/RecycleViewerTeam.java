package com.example.hunterz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import static android.widget.Toast.LENGTH_LONG;


public class RecycleViewerTeam extends RecyclerView.Adapter<RecycleViewerTeam.ViewHolder> {

    ArrayList<Team> team= new ArrayList<>();
    Context context;
    FragmentManager f_manager;
    String typeSS;

    private Uri mImageUri; // Image URI
    Dialog myDialog;

    static Team teamPass;

    public RecycleViewerTeam(ArrayList<Team> team, Context context,FragmentManager f_manager,String typeSS) {
        this.team = team;
        this.context = context;
        this.f_manager = f_manager;
        this.typeSS = typeSS;
    }

    @NonNull
    @Override
    public RecycleViewerTeam.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(typeSS.equals("team"))
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View teamDetail = layoutInflater.inflate(R.layout.team_view, parent, false);
            ViewHolder viewHolder = new ViewHolder(teamDetail);
            return viewHolder;
        }
        else if(typeSS.equals("player"))
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View teamDetail = layoutInflater.inflate(R.layout.player_card, parent, false);
            ViewHolder viewHolder = new ViewHolder(teamDetail);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if(typeSS.equals("team")) {
            String getId = team.get(position).getTeamId();
            String name = getId.substring(0, 3);
            String type = "";

            if (name.equals("CTM")) {
                type = "CRICKET";
            } else if (name.equals("FTM")) {
                type = "FOOTBALL";
            } else if (name.equals("VTM")) {
                type = "VOLLEYBALL";
            }
            holder.teamType.setText(type);
            holder.teamName.setText(team.get(position).getTeamName());
            holder.teamId.setText(team.get(position).getTeamId());

            final String finalType = type;
            holder.relativeLayoutTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    teamPass = new Team(team.get(position).getTeamId(), team.get(position).getTeamName(), team.get(position).getCaptain(),
                            team.get(position).getPlayer2(), team.get(position).getPlayer3(), team.get(position).getPlayer4(), team.get(position).getPlayer5(),
                            team.get(position).getPlayer6(), team.get(position).getPlayer7(), team.get(position).getPlayer8(), team.get(position).getPlayer9(),
                            team.get(position).getPlayer10(), team.get(position).getPlayer11(), team.get(position).getPlayer12(), team.get(position).getPlayer13(),
                            team.get(position).getPlayer14(), team.get(position).getPlayer15());

                    //To Send Data
                    TeanCricketDetails fragmentB;
                    fragmentB = new TeanCricketDetails();
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.teamV, fragmentB);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return team.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView teamType,teamId,teamName;
        RelativeLayout relativeLayoutTeam;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // All Team View
            teamType = itemView.findViewById(R.id.team_type);
            teamId = itemView.findViewById(R.id.team_id);
            teamName = itemView.findViewById(R.id.team_name);
            relativeLayoutTeam = itemView.findViewById(R.id.team_viewDetails);



        }
    }


}
