package com.example.hunterz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.sql.Blob;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.hunterz.R.id.member_image;
import static java.security.AccessController.getContext;


public class RecycleViewerMember extends RecyclerView.Adapter<RecycleViewerMember.ViewHolder> {

    ArrayList<Member> member = new ArrayList<>();
    ArrayList<Member> user = new ArrayList<>();
    private String type, get;
    private String IDS="",idN,id,image;
    private FirebaseAuth mAuth;
    Context context;
    Dialog myDialog;
    private Bitmap imageMember;
    View view;
    private String[] value = new String[10];
    private String[] dataM = new String[11];

    public RecycleViewerMember(ArrayList<Member> member, String type,Context context) {
        this.member = member;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewerMember.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (type.equals("view")) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View memberDetail = layoutInflater.inflate(R.layout.member_view, parent, false);
            final ViewHolder viewHolder = new ViewHolder(memberDetail);

            viewHolder.relativeLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog = new Dialog(context);
                    myDialog.setContentView(R.layout.fragment_new_member_details);

                    TextView fullName, phoneNo, emailId, nicNo, address, password,gender,dateOfBirth,sportType;
                    RelativeLayout relativeLayoutDetail;

                    ImageView memberImageDetails = myDialog.findViewById(R.id.profile_pic);
                    fullName = myDialog.findViewById(R.id.profile_name);
                    emailId = myDialog.findViewById(R.id.profile_email);
                    phoneNo = myDialog.findViewById(R.id.member_phone);
                    nicNo = myDialog.findViewById(R.id.member_nic);
                    address = myDialog.findViewById(R.id.member_address);
                    gender = myDialog.findViewById(R.id.member_gender);
                    dateOfBirth = myDialog.findViewById(R.id.member_dateOfBirth);
                    sportType = myDialog.findViewById(R.id.member_sport);
                    password = myDialog.findViewById(R.id.member_password);
                    relativeLayoutDetail = myDialog.findViewById(R.id.detailLayout);

                    memberImageDetails.setImageBitmap(member.get(viewHolder.getAdapterPosition()).getImage());
                    fullName.setText(member.get(viewHolder.getAdapterPosition()).getFullName());
                    emailId.setText(member.get(viewHolder.getAdapterPosition()).getEmail());
                    phoneNo.setText(member.get(viewHolder.getAdapterPosition()).getPhoneNo());
                    nicNo.setText(member.get(viewHolder.getAdapterPosition()).getNicNo());
                    address.setText(member.get(viewHolder.getAdapterPosition()).getAddress());
                    gender.setText(member.get(viewHolder.getAdapterPosition()).getGender());
                    dateOfBirth.setText(member.get(viewHolder.getAdapterPosition()).getDateOfBirth());
                    sportType.setText(member.get(viewHolder.getAdapterPosition()).getSportType());
                    password.setText(member.get(viewHolder.getAdapterPosition()).getPassword());

                    String status = member.get(viewHolder.getAdapterPosition()).getStatus();

                    if (status.equals("Activate")) {
                        relativeLayoutDetail.setBackgroundResource(R.drawable.activate_detail);
                    } else if (status.equals("Deactivate")) {
                        relativeLayoutDetail.setBackgroundResource(R.drawable.deactivate_detail);
                    }

                    myDialog.show();
                }
            });

            return viewHolder;
        }
        else if (type.equals("new")) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View memberDetail = layoutInflater.inflate(R.layout.member_new, parent, false);
            final ViewHolder viewHolder = new ViewHolder(memberDetail);

            // Click for all details
            viewHolder.relativeLayoutNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myDialog = new Dialog(context);
                    myDialog.setContentView(R.layout.fragment_new_member_details);

                    TextView fullName, phoneNo, emailId, nicNo, address, password,gender,dateOfBirth,sportType;

                    ImageView memberImageDetails = myDialog.findViewById(R.id.profile_pic);
                    fullName = myDialog.findViewById(R.id.profile_name);
                    emailId = myDialog.findViewById(R.id.profile_email);
                    phoneNo = myDialog.findViewById(R.id.member_phone);
                    nicNo = myDialog.findViewById(R.id.member_nic);
                    address = myDialog.findViewById(R.id.member_address);
                    gender = myDialog.findViewById(R.id.member_gender);
                    dateOfBirth = myDialog.findViewById(R.id.member_dateOfBirth);
                    sportType = myDialog.findViewById(R.id.member_sport);
                    password = myDialog.findViewById(R.id.member_password);

                    memberImageDetails.setImageBitmap(member.get(viewHolder.getAdapterPosition()).getImage());
                    fullName.setText(member.get(viewHolder.getAdapterPosition()).getFullName());
                    emailId.setText(member.get(viewHolder.getAdapterPosition()).getEmail());
                    phoneNo.setText(member.get(viewHolder.getAdapterPosition()).getPhoneNo());
                    nicNo.setText(member.get(viewHolder.getAdapterPosition()).getNicNo());
                    address.setText(member.get(viewHolder.getAdapterPosition()).getAddress());
                    gender.setText(member.get(viewHolder.getAdapterPosition()).getGender());
                    dateOfBirth.setText(member.get(viewHolder.getAdapterPosition()).getDateOfBirth());
                    sportType.setText(member.get(viewHolder.getAdapterPosition()).getSportType());
                    password.setText(member.get(viewHolder.getAdapterPosition()).getPassword());

                    myDialog.show();
                }
            });

            return viewHolder;
        }
        else if(type.equals("status"))
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View memberDetail = layoutInflater.inflate(R.layout.status_view, parent, false);
            final ViewHolder viewHolder = new ViewHolder(memberDetail);

            viewHolder.relativeLayoutStatus.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    myDialog = new Dialog(context);
                    myDialog.setContentView(R.layout.fragment_new_member_details);

                    TextView fullName, phoneNo, emailId, nicNo, address, password,gender,dateOfBirth,sportType;
                    RelativeLayout relativeLayoutDetail;

                    ImageView memberImageDetails = myDialog.findViewById(R.id.profile_pic);
                    fullName = myDialog.findViewById(R.id.profile_name);
                    emailId = myDialog.findViewById(R.id.profile_email);
                    phoneNo = myDialog.findViewById(R.id.member_phone);
                    nicNo = myDialog.findViewById(R.id.member_nic);
                    address = myDialog.findViewById(R.id.member_address);
                    gender = myDialog.findViewById(R.id.member_gender);
                    dateOfBirth = myDialog.findViewById(R.id.member_dateOfBirth);
                    sportType = myDialog.findViewById(R.id.member_sport);
                    password = myDialog.findViewById(R.id.member_password);
                    relativeLayoutDetail = myDialog.findViewById(R.id.detailLayout);

                    memberImageDetails.setImageBitmap(member.get(viewHolder.getAdapterPosition()).getImage());
                    fullName.setText(member.get(viewHolder.getAdapterPosition()).getFullName());
                    emailId.setText(member.get(viewHolder.getAdapterPosition()).getEmail());
                    phoneNo.setText(member.get(viewHolder.getAdapterPosition()).getPhoneNo());
                    nicNo.setText(member.get(viewHolder.getAdapterPosition()).getNicNo());
                    address.setText(member.get(viewHolder.getAdapterPosition()).getAddress());
                    gender.setText(member.get(viewHolder.getAdapterPosition()).getGender());
                    dateOfBirth.setText(member.get(viewHolder.getAdapterPosition()).getDateOfBirth());
                    sportType.setText(member.get(viewHolder.getAdapterPosition()).getSportType());
                    password.setText(member.get(viewHolder.getAdapterPosition()).getPassword());

                    String status = member.get(viewHolder.getAdapterPosition()).getStatus();

                    if (status.equals("Activate")) {
                       relativeLayoutDetail.setBackgroundResource(R.drawable.activate_detail);
                    } else if (status.equals("Deactivate")) {
                        relativeLayoutDetail.setBackgroundResource(R.drawable.deactivate_detail);
                    }

                    myDialog.show();
                }
            });

            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (type.equals("view")) {
            holder.memberImage.setImageBitmap(member.get(position).getImage());
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
        } else if (type.equals("new")) {
            idN = member.get(position).getId();
            Log.d("Pendiing",idN);
//            image = member.get(position).getImage();
            holder.memberImageView.setImageBitmap(member.get(position).getImage());
            holder.memberNameView.setText(member.get(position).getFullName());
            holder.memberEmailView.setText(member.get(position).getEmail());
            holder.memberNicView.setText(member.get(position).getNicNo());
            holder.memberPhoneView.setText(member.get(position).getPhoneNo());

            // Click On Accept button
            holder.accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view = v;
                  insertToMember(idN);
                  delete(idN); // Delete the selected record

                }
            });

            // Click On Refuse button
            holder.refuse_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete(idN); // Delete the selected record
                    Toast.makeText(context,"Member Refused",LENGTH_LONG);
                }
            });

        }
        else if(type.equals("status"))
        {
            id = member.get(position).getId();
            final String idStatus = id;
            holder.memberImageStatus.setImageBitmap(member.get(position).getImage());
            holder.memberNameStatus.setText(member.get(position).getFullName());
            holder.memberNicNoStatus.setText(member.get(position).getNicNo());
            holder.memberEmailStatus.setText(member.get(position).getEmail());

            final String status = member.get(position).getStatus();

            if (status.equals("Activate")) {
                holder.memberStatusLineStatus.setBackgroundColor(Color.parseColor("#3b944b"));
            } else if (status.equals("Deactivate")) {
                holder.memberStatusLineStatus.setBackgroundColor(Color.parseColor("#b42959"));
            }

            holder.activateStatusBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    changeStatus("Activate",idStatus);
                    holder.memberStatusLineStatus.setBackgroundColor(Color.parseColor("#3b944b"));
                    Toast.makeText(v.getContext(),"Activate", Toast.LENGTH_LONG).show();
                }
            });

            holder.deactivateStatusBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    changeStatus("Deactivate",idStatus);
                    holder.memberStatusLineStatus.setBackgroundColor(Color.parseColor("#b42959"));
                    Toast.makeText(v.getContext(),"Deactivate", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    public void insertToMember(String mem_id)
    {
        DatabaseHandler db=new DatabaseHandler(context);
        byte[] mImage = null;
        String authId="";
        Cursor res =null;

        res = db.getMember("SELECT * FROM pendingMember_Table WHERE member_id ='"+mem_id+"'");

        Log.d("Pending ID",mem_id);

        if(res.moveToFirst())
        {
            authId = generateID("AUN","select authentication_id from authentication_Table");
            dataM[0] = generateID("HUN","select member_id from member_Table");
            dataM[1] = res.getString(1);
            dataM[2] = res.getString(2);
            dataM[3] = res.getString(3);
            dataM[4] = res.getString(4);
            dataM[5] = res.getString(5);
            dataM[6] = res.getString(6);
            dataM[7] = res.getString(7);
            dataM[8] = res.getString(8);
            dataM[9] = res.getString(9);
            dataM[10] = "Activate";
            mImage = res.getBlob(10);
        }


        DatabaseHandler dbHandler = new DatabaseHandler(context);

        boolean result = dbHandler.insertMember(dataM[0],dataM[1],dataM[2],dataM[3],dataM[4],dataM[5],dataM[6],dataM[7],dataM[10],mImage,
                "admin001",authId,dataM[9],dataM[8],"Member"); // Insert Method

        if(result == true) {
            Toast.makeText(context,"Successfully Added!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context,"Error While Adding", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return member.size();
    }

    // Change member Status
    public void changeStatus(String status,String id)
    {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean result = db.updateMemberStatus(id,status);

        if(result == true)
        {
          //  Toast.makeText(context,"Member Status Changed!", LENGTH_LONG).show();
        } else
        {
            //Toast.makeText(context,"Error While Changing", LENGTH_LONG).show();
        }
    }

    public void authentication(String email, String password, final View v) {
        mAuth = FirebaseAuth.getInstance();

        if (value[2] != "" && value[8] != "") {
            mAuth.createUserWithEmailAndPassword(email, password).
                    addOnCompleteListener((Activity) v.getContext(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Success", "createUserWithEmail:success");
                                Toast.makeText(v.getContext(), "Accepted!" , Toast.LENGTH_LONG).show();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Not Success", "createUserWithEmail:failure", task.getException());

                            }
                        }
                    });
        }
    }

    public void delete(String value) // Delete Pending member
    {
        DatabaseHandler db = new DatabaseHandler(context);

        db.deletePendingMember(value);
    }

    public String generateID(String id, String query) // Auto Generate ID
    {
        DatabaseHandler db = new DatabaseHandler(context);

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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView memberName,memberId,memberEmail,memberPhone,memberNic,memberStatus,
         memberNameView,memberEmailView,memberPhoneView,memberNicView,memberNameStatus,memberNicNoStatus,memberEmailStatus,memberStatusLineStatus;
        Button accept_btn,refuse_btn,activateStatusBtn,deactivateStatusBtn;
        CircleImageView memberImage,memberImageStatus,memberImageView;

        RelativeLayout relativeLayoutView,relativeLayoutNew,relativeLayoutStatus,relativeLayoutDetail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // View
            memberImage = itemView.findViewById(member_image);
            memberName = itemView.findViewById(R.id.member_name);
            memberId = itemView.findViewById(R.id.member_id);
            memberEmail = itemView.findViewById(R.id.member_email);
            memberPhone = itemView.findViewById(R.id.member_phone);
            memberNic = itemView.findViewById(R.id.member_nic);
            memberStatus = itemView.findViewById(R.id.status_line);
            relativeLayoutView = itemView.findViewById(R.id.member_viewDetails);

            // New
            memberNameView = itemView.findViewById(R.id.member_name_view);
            memberEmailView = itemView.findViewById(R.id.member_email_view);
            memberPhoneView = itemView.findViewById(R.id.member_phone_view);
            memberNicView = itemView.findViewById(R.id.member_nic_view);
            memberImageView = itemView.findViewById(R.id.member_image_view);
            accept_btn = itemView.findViewById(R.id.member_accept_btn);
            refuse_btn = itemView.findViewById(R.id.member_refuse_btn);
            relativeLayoutNew = itemView.findViewById(R.id.member_new);

            //Status
            memberImageStatus = itemView.findViewById(R.id.member_image_status);
            memberNameStatus = itemView.findViewById(R.id.member_name);
            memberNicNoStatus = itemView.findViewById(R.id.member_nic);
            memberEmailStatus = itemView.findViewById(R.id.member_email);
            activateStatusBtn = itemView.findViewById(R.id.member_activate_btn);
            deactivateStatusBtn = itemView.findViewById(R.id.member_deactivate_btn);
            memberStatusLineStatus = itemView.findViewById(R.id.status_line);
            relativeLayoutStatus = itemView.findViewById(R.id.image_status);
            relativeLayoutDetail = itemView.findViewById(R.id.detailLayout);
        }
    }
}
