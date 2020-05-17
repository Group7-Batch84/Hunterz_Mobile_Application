package com.example.hunterz;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.hunterz.R.id.member_image;

public class RecycleViewerMember extends RecyclerView.Adapter<RecycleViewerMember.ViewHolder>{

    ArrayList<Member> member = new ArrayList<>();

    public RecycleViewerMember(ArrayList<Member> member) {
        this.member = member;
    }

    @NonNull
    @Override
    public RecycleViewerMember.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View memberDetail = layoutInflater.inflate(R.layout.member_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(memberDetail);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(member.get(position).getImage()).into(holder.memberImage);
        holder.memberName.setText(member.get(position).getFullName());
        holder.memberEmail.setText(member.get(position).getEmail());
        holder.memberId.setText(member.get(position).getId());
        holder.memberNic.setText(member.get(position).getNicNo());
        holder.memberPhone.setText(member.get(position).getPhoneNo());

        String status =  member.get(position).getStatus();

        if(status.equals("Activate"))
        {
            holder.memberStatus.setBackgroundColor(Color.parseColor("#3b944b"));
        }
        else if(status.equals("Deactivate"))
        {
            holder.memberStatus.setBackgroundColor(Color.parseColor("#b42959"));
        }

    }

    @Override
    public int getItemCount() {
        return member.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView memberImage;
        TextView memberName,memberId,memberEmail,memberPhone,memberNic,memberStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            memberImage = itemView.findViewById(member_image);
            memberName = itemView.findViewById(R.id.member_name);
            memberId = itemView.findViewById(R.id.member_id);
            memberEmail = itemView.findViewById(R.id.member_email);
            memberPhone = itemView.findViewById(R.id.member_phone);
            memberNic = itemView.findViewById(R.id.member_nic);
            memberStatus = itemView.findViewById(R.id.status_line);
        }
    }
}
