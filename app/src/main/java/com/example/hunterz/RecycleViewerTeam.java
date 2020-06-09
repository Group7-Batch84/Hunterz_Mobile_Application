package com.example.hunterz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import java.util.ArrayList;
import static android.widget.Toast.LENGTH_LONG;


public class RecycleViewerTeam extends RecyclerView.Adapter<RecycleViewerTeam.ViewHolder> {

    ArrayList<Team> team= new ArrayList<>();
    Context context;
    String teamTypeS;

    private Uri mImageUri; // Image URI
    Dialog myDialog;



    public RecycleViewerTeam(ArrayList<Team> team, Context context,String teamTypeS) {
        this.team = team;
        this.context = context;
        this.teamTypeS = teamTypeS;
    }

    @NonNull
    @Override
    public RecycleViewerTeam.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(teamTypeS.equals("cricket"))
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View teamDetail = layoutInflater.inflate(R.layout.team_view, parent, false);
            final ViewHolder viewHolder = new ViewHolder(teamDetail);

                viewHolder.relativeLayoutTeam.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {

                        //To Send Data
//                        TeanCricketDetails fragmentB = new TeanCricketDetails();
//                        Bundle bundle=new Bundle();
//                        bundle.putString("NAME","");
//                        fragmentB.setArguments(bundle);

//                        Fragment fragment = new TeanCricketDetails();
//                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.teamView, fragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();

                        AppCompatActivity activity = (AppCompatActivity) context;
                        Fragment myFragment = new TeanCricketDetails();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.teamView, myFragment).addToBackStack(null).commit();

                    }
                });

            return viewHolder;
        }
        else if(teamTypeS.equals("football"))
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View teamDetail = layoutInflater.inflate(R.layout.team_view, parent, false);
            final ViewHolder viewHolder = new ViewHolder(teamDetail);

            viewHolder.relativeLayoutTeam.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {


                }
            });

            return viewHolder;
        }
        else if(teamTypeS.equals("volleyball"))
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View teamDetail = layoutInflater.inflate(R.layout.team_view, parent, false);
            final ViewHolder viewHolder = new ViewHolder(teamDetail);

            viewHolder.relativeLayoutTeam.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {


                }
            });

            return viewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(teamTypeS.equals("cricket"))
        {
            holder.teamType.setText("CRICKET");
            holder.teamType.setText(team.get(position).getTeamName());
            holder.teamId.setText(team.get(position).getTeamId());
        }
        else if(teamTypeS.equals("football"))
        {
            holder.teamType.setText("FOOTBALL");
            holder.teamType.setText(team.get(position).getTeamName());
            holder.teamId.setText(team.get(position).getTeamId());
        }
        else if(teamTypeS.equals("volleyball"))
        {
            holder.teamType.setText("VOLLEYBALL");
            holder.teamType.setText(team.get(position).getTeamName());
            holder.teamId.setText(team.get(position).getTeamId());
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
