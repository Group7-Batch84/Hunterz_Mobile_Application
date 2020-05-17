package com.example.hunterz;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


public class SearchMember extends Fragment {

    private Spinner selectSearch;
    private EditText searchMember,fullName,phoneNo,emailId,nicNo,address,password;
    private RadioGroup gender;
    private RadioButton maleRad,femaleRad;
    private CheckBox cricketChk,footballChk,volleyballChk;
    private Button datePicker_Btn,searchBtn,changeBtn,clearBtn,updateBtn;
    private TextView selectedDate;
    private ImageView memberImage;
    private String selectedSearchType;
    private Uri mImageUri;

    DatabaseReference databaseReference;

    Calendar calendar;
    DatePickerDialog datePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_member, container, false);

        selectSearch = view.findViewById(R.id.select_search);
        searchMember = view.findViewById(R.id.search_txt);

        fullName = view.findViewById(R.id.fullName_txt);
        phoneNo = view.findViewById(R.id.phoneNo_txt);
        emailId = view.findViewById(R.id.email_txt);
        nicNo = view.findViewById(R.id.nic_txt);
        address = view.findViewById(R.id.address_txt);
        password = view.findViewById(R.id.address_txt);
        gender = view.findViewById(R.id.radioGroupGender);
        maleRad = view.findViewById(R.id.male_rad);
        femaleRad = view.findViewById(R.id.female_rad);
        cricketChk = view.findViewById(R.id.cricket_chk);
        footballChk = view.findViewById(R.id.football_chk);
        volleyballChk = view.findViewById(R.id.volleyball_chk);
        searchBtn = view.findViewById(R.id.search_btn);
        changeBtn = view.findViewById(R.id.change_btn);
        clearBtn = view.findViewById(R.id.clear_btn);
        updateBtn = view.findViewById(R.id.update_btn);
        memberImage = view.findViewById(R.id.member_image);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectSearch.setAdapter(adapter);

        // Select Search Type
        selectSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString().trim();
                Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();
                if(value.equals("Register Number"))
                {
                    searchMember.setHint(getString(R.string.register_search_hint));
                    selectedSearchType = "Register Number";
                }
                else if(value.equals("NIC Number"))
                {
                    searchMember.setHint(getString(R.string.nic_search_hint));
                    selectedSearchType = "NIC Number";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        datePicker_Btn = view.findViewById(R.id.date_picker_btn);
        selectedDate = view.findViewById(R.id.selected_date);
        // Click "Date Picker" Button
        datePicker_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {

                        selectedDate.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });

        // On Click Search Member Details
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // On Click Change Member Image
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseFile(v);
            }
        });

        // On Click Clear Member Details
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDetails();
            }
        });

        // On Click Update Member Details
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }



    public void onChooseFile(View v) // To choose the insert file
    {
        CropImage.activity().start(getContext(),SearchMember.this);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) // To crop and select the Image
    {
        super.onActivityResult(reqCode,resultCode,data);

        if(reqCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                try
                {
                    mImageUri = result.getUri();
                    memberImage.setImageURI(mImageUri);

                } catch (Exception e) {
                    e.printStackTrace();
                    makeText(getContext(), getString(R.string.member_image_errorMessage1), LENGTH_LONG).show();
                }
            }
            else {
                makeText(getContext(), getString(R.string.member_image_errorMessage2), LENGTH_LONG).show();
            }
        }
    }

    public void clearDetails()  // Clear All Data fields
    {
        searchMember.getText().clear();
        searchMember.setEnabled(true);
        searchMember.requestFocus();

        memberImage.setImageDrawable(null);
        gender.clearCheck();
        fullName.getText().clear();
        phoneNo.getText().clear();
        emailId.getText().clear();
        nicNo.getText().clear();
        address.getText().clear();
        selectedDate.setText("DD/MM/YYYY");
        cricketChk.setChecked(false);
        volleyballChk.setChecked(false);
        footballChk.setChecked(false);
        password.getText().clear();
    }

}
