package com.example.hunterz.ui.pay_fee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hunterz.Member;
import com.example.hunterz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PayFeeFragment extends Fragment {

    private PayFeeViewModel payFeeViewModel;
    private Spinner searchType,selectMonth;
    private CircleImageView memberImage;
    private EditText searchTxt,amountTxt;
    private TextView memberName,memberEmail,memberPhone,memberNic;
    private Button payBtn,searchBtn,clearBtn;
    private RelativeLayout relativeLayout;
    private String selectedSearchType;
    private ProgressBar progressBar;

    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        payFeeViewModel = ViewModelProviders.of(this).get(PayFeeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_pay_fee, container, false);

        memberImage = view.findViewById(R.id.profile_pic);
        searchTxt = view.findViewById(R.id.search_txt);
        amountTxt = view.findViewById(R.id.amount_txt);
        memberName = view.findViewById(R.id.profile_name);
        memberEmail = view.findViewById(R.id.profile_email);
        memberPhone = view.findViewById(R.id.member_phone);
        memberNic = view.findViewById(R.id.member_nic);
        payBtn = view.findViewById(R.id.pay_btn);
        searchBtn = view.findViewById(R.id.search_btn);
        clearBtn = view.findViewById(R.id.clear_btn);
        relativeLayout = view.findViewById(R.id.searchlayout);
        progressBar = view.findViewById(R.id.progressBar1);

        // Drop down list (Search Type)
        searchType = view.findViewById(R.id.select_search);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(getActivity(),
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchType.setAdapter(adapterType);

        // Select Search Type
        searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString().trim();
                Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();
                if(value.equals("Register Number"))
                {
                    searchTxt.setHint(getString(R.string.register_search_hint));
                    selectedSearchType = "id";
                    clearDetails();
                }
                else if(value.equals("NIC Number"))
                {
                    searchTxt.setHint(getString(R.string.nic_search_hint));
                    selectedSearchType = "nicNo";
                    clearDetails();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Drop down list (Select Month)
        selectMonth = view.findViewById(R.id.select_month);
        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(getActivity(),
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectMonth.setAdapter(adapterMonth);

        // Click on search button
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                search(selectedSearchType);
            }
        });

        // Click on pay amount button
        payBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        //Click on Clear button
        clearBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clearDetails();
            }
        });

        return view;
    }

    public void search(String searchType)
    {
        if(!searchTxt.getText().toString().equals("")) {

            databaseReference = FirebaseDatabase.getInstance().getReference("Member");

            databaseReference.orderByChild(searchType).equalTo(searchTxt.getText().toString().toUpperCase())
                    .addValueEventListener(new ValueEventListener()
            {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {

                        ArrayList<Member> list = new ArrayList<>();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Member user = postSnapshot.getValue(Member.class);
                            list.add(user);
                        }

                        searchTxt.setEnabled(false);
                        memberName.setText(list.get(0).getFullName());
                        memberPhone.setText(list.get(0).getPhoneNo());
                        memberEmail.setText(list.get(0).getEmail());
                        memberNic.setText(list.get(0).getNicNo());
                        Picasso.get().load(list.get(0).getImage()).into(memberImage);
                        progressBar.setVisibility(View.GONE);
                        relativeLayout.setVisibility(View.VISIBLE);


                    }
                    else
                    {
                        searchTxt.setError("Invalid Search Value");
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.e("TAG", "Failed to read user", error.toException());
                }
            });
        }
        else
        {
            searchTxt.setError("Search Field is Empty");
        }
    }


    public void clearDetails()
    {
        relativeLayout.setVisibility(View.GONE);
        searchTxt.setEnabled(true);
        searchTxt.getText().clear();
        memberName.setText("");
        memberPhone.setText("");
        memberEmail.setText("");
        memberNic.setText("");
        memberImage.setImageDrawable(null);
    }

}