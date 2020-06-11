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
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TeanCricketDetails extends Fragment {

    DatabaseHandler db ;

    private Button deleteTeam;
    private TextView teamName,backBtn,teamType;

   private ImageView[] memberImage = new ImageView[15];
   private TextView[] memberName = new TextView[15];
   private TextView[] memberId = new TextView[15];
   private TextView[] memberType = new TextView[15];

   RelativeLayout relativeLayout;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tean_cricket_details, container, false);


        db = new DatabaseHandler(getContext());

        teamName = view.findViewById(R.id.team_nameC);
        deleteTeam = view.findViewById(R.id.delete_teambtn);
        relativeLayout = view.findViewById(R.id.detailLayout);
        backBtn = view.findViewById(R.id.back);
        teamType = view.findViewById(R.id.teamType);

        relativeLayout.bringToFront();


        for(int i = 0; i < 15; i++ )
        {
            memberImage[i] = view.findViewById(R.id.member_image+(i+1));
            memberName[i] = view.findViewById(R.id.member_name+(i+1));
            memberId[i] = view.findViewById(R.id.member_idC+(i+1));
            memberType[i] = view.findViewById(R.id.member_type+(i+1));
        }

        teamName.setText(RecycleViewerTeam.teamPass.getTeamName());

        teamTypeTitle();

        viewPlayer(RecycleViewerTeam.teamPass.getCaptain(),0);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer2(),1);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer3(),2);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer4(),3);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer5(),4);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer6(),5);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer7(),6);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer8(),7);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer9(),8);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer10(),9);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer11(),10);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer12(),11);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer13(),12);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer14(),13);
        viewPlayer(RecycleViewerTeam.teamPass.getPlayer15(),14);


        deleteTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(RecycleViewerTeam.teamPass.getTeamId());
                Toast.makeText(getContext(),"Team Deleted!",Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().beginTransaction().remove(TeanCricketDetails.this).commit();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(TeanCricketDetails.this).commit();
            }
        });

        return view;
    }

    public void viewPlayer(String memberID,int plyer)
    {
        Cursor res = null;
        res = db.getMember("SELECT member_name,member_id,member_image FROM member_Table WHERE member_id='" +memberID+ "'");

        if(res.moveToFirst())
        {
            byte[] image = res.getBlob(2);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

            memberName[plyer].setText(res.getString(0));
            memberId[plyer].setText(res.getString(1));
            memberImage[plyer].setImageBitmap(bmp);
            memberType[plyer].setText("Player");
        }
    }

    public void delete(String value) // Delete Team
    {
        DatabaseHandler db = new DatabaseHandler(getContext());

        db.deleteCricketTeam(value);
    }

    public void teamTypeTitle()
    {
        String getId = RecycleViewerTeam.teamPass.getTeamId();
        String name = getId.substring(0, 3);
        String type = "";

        if (name.equals("CTM")) {
            type = "CRICKET";
        } else if (name.equals("FTM")) {
            type = "FOOTBALL";
        } else if (name.equals("VTM")) {
            type = "VOLLEYBALL";
        }
        teamType.setText(type);
    }


}
