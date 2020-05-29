package com.example.hunterz;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.hunterz.R.id.member_image;



public class RecycleViewerMember extends RecyclerView.Adapter<RecycleViewerMember.ViewHolder> {

    private DatabaseReference databaseReference, dataRef;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    ArrayList<Member> member = new ArrayList<>();
    ArrayList<Member> user = new ArrayList<>();
    private String type, get;
    private String IDS="",id,image;
    private FirebaseAuth mAuth;
    Context context;
    Dialog myDialog;
    View view;
    private String[] value = new String[11];

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
            ViewHolder viewHolder = new ViewHolder(memberDetail);
            return viewHolder;
        } else if (type.equals("new")) {
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

                    Picasso.get().load(member.get(viewHolder.getAdapterPosition()).getImage()).into(memberImageDetails);
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

                    Picasso.get().load(member.get(viewHolder.getAdapterPosition()).getImage()).into(memberImageDetails);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        databaseReference = FirebaseDatabase.getInstance().getReference("Pending_Member");

        dataRef = FirebaseDatabase.getInstance().getReference("Member");

        if (type.equals("view")) {
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
        } else if (type.equals("new")) {
            id = member.get(position).getId();
            image = member.get(position).getImage();
            Picasso.get().load(member.get(position).getImage()).into(holder.memberImageView);
            holder.memberNameView.setText(member.get(position).getFullName());
            holder.memberEmailView.setText(member.get(position).getEmail());
            holder.memberNicView.setText(member.get(position).getNicNo());
            holder.memberPhoneView.setText(member.get(position).getPhoneNo());

            // Click On Accept button
            holder.accept_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view = v;
                  new Get().execute();

                }
            });

            // Click On Refuse button
            holder.refuse_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //delete(id); // Delete the selected record
                    //deletePic(image);
                    Toast.makeText(v.getContext(),"REFUSE", Toast.LENGTH_LONG).show();
                }
            });

        }
        else if(type.equals("status"))
        {
            Picasso.get().load(member.get(position).getImage()).into(holder.memberImageStatus);
            holder.memberNameStatus.setText(member.get(position).getFullName());
            holder.memberNicNoStatus.setText(member.get(position).getNicNo());
            holder.memberEmailStatus.setText(member.get(position).getEmail());

            String status = member.get(position).getStatus();

            if (status.equals("Activate")) {
                holder.memberStatusLineStatus.setBackgroundColor(Color.parseColor("#3b944b"));
            } else if (status.equals("Deactivate")) {
                holder.memberStatusLineStatus.setBackgroundColor(Color.parseColor("#b42959"));
            }

            holder.activateStatusBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Activate", Toast.LENGTH_LONG).show();
                }
            });

            holder.deactivateStatusBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Deactivate", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    int i=0;
    class Get extends AsyncTask<String,String,String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {

            i=0;
            dataRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int count = 0;
                    String idType = "";
                    ArrayList<Member> list = new ArrayList<>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Member user = postSnapshot.getValue(Member.class);
                        list.add(user);
                        count++;
                    }

                    if (count > 0) {

                        idType = list.get(list.size() - 1).getId().toString();
                        String x = idType.substring(3);
                        int ID = Integer.parseInt(x);

                        if (ID > 0 && ID < 9) {
                            ID = ID + 1;
                            IDS = "HUN" + "00" + ID;
                        } else if (ID >= 9 && ID < 99) {
                            ID = ID + 1;
                            IDS = "HUN" + "0" + ID;
                        } else if (ID >= 99) {
                            ID = ID + 1;
                            IDS = "HUN" + ID;
                        }

                    } else {
                        IDS = "HUN" + "001";
                    }

                    Log.d("ID", IDS);
                    databaseReference.orderByChild("id").equalTo(id).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                Member user1 = postSnapshot.getValue(Member.class);
                                user.add(user1);
                            }

                            if (i==0) {

                                value[0] = user.get(0).getFullName();
                                value[1] = user.get(0).getPhoneNo();
                                value[2] = user.get(0).getEmail();
                                value[3] = user.get(0).getNicNo();
                                value[4] = user.get(0).getGender();
                                value[5] = user.get(0).getAddress();
                                value[6] = user.get(0).getDateOfBirth();
                                value[7] = user.get(0).getSportType();
                                value[8] = user.get(0).getPassword();
                                value[9] = "Activate";
                                value[10] = user.get(0).getImage();

                                Member member = new Member(IDS, value[0], value[1], value[2], value[3], value[4], value[5], value[6], value[7], value[8], value[9], value[10]);
                                dataRef.child(IDS).setValue(member);
                                i++;
                            }
                            authentication(value[2],value[8],view);
                            delete(user.get(0).getId());
                            //Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.e("TAG", "Failed to read user", error.toException());
                        }
                    });


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.e("TAG", "Failed to read user", error.toException());
                }

            });
            return null;
        }
        protected void onPostExecute(String file_url) {

            Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show();
//            runOnUiThread(new Runnable() {
//                public void run() {
//                    Toast.makeText(getApplicationContext(), "Success" , Toast.LENGTH_LONG).show();
//                }
//            });
        }
    }


    @Override
    public int getItemCount() {
        return member.size();
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

    public void generateID(final String id,final int length,String table) // Auto Generate ID
    {
        databaseReference = FirebaseDatabase.getInstance().getReference(table);

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                String idType = "";
                ArrayList<Member> list = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Member user = postSnapshot.getValue(Member.class);
                    list.add(user);
                    count++;
                }

                if (count > 0) {

                    idType = list.get(list.size() - 1).getId().toString();
                    String x = idType.substring(length);
                    int ID = Integer.parseInt(x);

                    if (ID > 0 && ID < 9) {
                        ID = ID + 1;
                        IDS = id + "00" + ID;
                    } else if (ID >= 9 && ID < 99) {
                        ID = ID + 1;
                        IDS = id + "0" + ID;
                    } else if (ID >= 99) {
                        ID = ID + 1;
                        IDS = id + ID;
                    }

                } else {
                    IDS = id + "001";
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("TAG", "Failed to read user", error.toException());
            }

        });
    }

    public void deletePic(String imageUrl)
    {
        storageReference = firebaseStorage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Succes","Deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error","Error while Deleting");
            }
        });
    }


    public void delete(String value) // Delete
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("Pending_Member");

        databaseReference.child(value).removeValue();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView memberImage,memberImageView;
        TextView memberName,memberId,memberEmail,memberPhone,memberNic,memberStatus,
         memberNameView,memberEmailView,memberPhoneView,memberNicView,memberNameStatus,memberNicNoStatus,memberEmailStatus,memberStatusLineStatus;
        Button accept_btn,refuse_btn,activateStatusBtn,deactivateStatusBtn;
        CircleImageView memberImageStatus;

        RelativeLayout relativeLayoutNew,relativeLayoutStatus,relativeLayoutDetail;


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
            memberImageStatus = itemView.findViewById(member_image);
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
