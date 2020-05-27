package com.example.hunterz;

import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.hunterz.R.id.member_image;

public class RecycleViewerMember extends RecyclerView.Adapter<RecycleViewerMember.ViewHolder>{

    ArrayList<Member> member = new ArrayList<>();
    String type;

    public RecycleViewerMember(ArrayList<Member> member, String type) {
        this.member = member;
        this.type = type;
    }

    @NonNull
    @Override
    public RecycleViewerMember.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(type.equals("view")) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View memberDetail = layoutInflater.inflate(R.layout.member_view, parent, false);
            ViewHolder viewHolder = new ViewHolder(memberDetail);
            return viewHolder;
        }
        else if (type.equals("new"))
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View memberDetail = layoutInflater.inflate(R.layout.member_new, parent, false);
            ViewHolder viewHolder = new ViewHolder(memberDetail);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(type.equals("view")) {
            Picasso.get().load(member.get(position).getImage()).into(holder.memberImage);
            holder.memberName.setText(member.get(position).getFullName());
            holder.memberEmail.setText(member.get(position).getEmail());
            holder.memberId.setText(member.get(position).getId());
            holder.memberNic.setText(member.get(position).getNicNo());
            holder.memberPhone.setText(member.get(position).getPhoneNo());

            String status = member.get(position).getStatus();

            if (status.equals("Activate")) {
                holder.memberStatus.setBackgroundColor(Color.parseColor("#3b944b"));
            } else if (status.equals("Deactivate")) {
                holder.memberStatus.setBackgroundColor(Color.parseColor("#b42959"));
            }
        }
        else if(type.equals("new"))
        {
            holder.memberNameView.setText(member.get(position).getFullName());
            holder.memberEmailView.setText(member.get(position).getEmail());
            holder.memberNicView.setText(member.get(position).getNicNo());
            holder.memberPhoneView.setText(member.get(position).getPhoneNo());

            holder.relativeLayoutNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"All", Toast.LENGTH_SHORT).show();
                }
            });

            // Click On Accept button
            holder.accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Accept", Toast.LENGTH_SHORT).show();
                }
            });

            // Click On Refuse button
            holder.refuse_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Refuse",Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return member.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView memberImage;
        TextView memberName,memberId,memberEmail,memberPhone,memberNic,memberStatus,
         memberNameView,memberEmailView,memberPhoneView,memberNicView;
        Button accept_btn,refuse_btn;
        RelativeLayout relativeLayoutNew;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            memberImage = itemView.findViewById(member_image);
            memberName = itemView.findViewById(R.id.member_name);
            memberId = itemView.findViewById(R.id.member_id);
            memberEmail = itemView.findViewById(R.id.member_email);
            memberPhone = itemView.findViewById(R.id.member_phone);
            memberNic = itemView.findViewById(R.id.member_nic);
            memberStatus = itemView.findViewById(R.id.status_line);

            memberNameView = itemView.findViewById(R.id.member_name_view);
            memberEmailView = itemView.findViewById(R.id.member_email_view);
            memberPhoneView = itemView.findViewById(R.id.member_phone_view);
            memberNicView = itemView.findViewById(R.id.member_nic_view);
            accept_btn = itemView.findViewById(R.id.member_accept_btn);
            refuse_btn = itemView.findViewById(R.id.member_refuse_btn);
            relativeLayoutNew = itemView.findViewById(R.id.member_new);
        }
    }
}
