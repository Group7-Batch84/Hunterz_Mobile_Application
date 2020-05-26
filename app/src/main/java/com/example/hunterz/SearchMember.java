package com.example.hunterz;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;


public class SearchMember extends Fragment {

    private Spinner selectSearch;
    private EditText searchMember,fullName,phoneNo,emailId,nicNo,address,newPassword;
    private RadioGroup gender;
    private RadioButton maleRad,femaleRad;
    private CheckBox cricketChk,footballChk,volleyballChk,newPasswordChk;
    private Button datePicker_Btn,searchBtn,changeBtn,clearBtn,updateBtn;
    private TextView selectedDate,genderError,sportTypeError,dobError,password,memberId;
    private ImageView memberImage;
    private String selectedSearchType;
    private Uri mImageUri;

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year;

    String[] value = new String[12];
    Validation valid = new Validation();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_member, container, false);

        selectSearch = view.findViewById(R.id.select_search);
        searchMember = view.findViewById(R.id.search_txt);

        memberId = view.findViewById(R.id.member_idS);
        fullName = view.findViewById(R.id.fullName_txt);
        phoneNo = view.findViewById(R.id.phoneNo_txt);
        emailId = view.findViewById(R.id.email_txt);
        nicNo = view.findViewById(R.id.nic_txt);
        address = view.findViewById(R.id.address_txt);
        newPasswordChk = view.findViewById(R.id.newPassword_chk);
        newPassword = view.findViewById(R.id.newPassword_txt);
        password = view.findViewById(R.id.password);
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
        genderError = view.findViewById(R.id.gender_error);
        sportTypeError = view.findViewById(R.id.sport_type_error);
        dobError = view.findViewById(R.id.dob_error);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectSearch.setAdapter(adapter);

        checkNewpassword(newPasswordChk);

        // Select Search Type
        selectSearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString().trim();
                Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();
                if(value.equals("Register Number"))
                {
                    searchMember.setHint(getString(R.string.register_search_hint));
                    selectedSearchType = "id";
                    clearDetails();
                }
                else if(value.equals("NIC Number"))
                {
                    searchMember.setHint(getString(R.string.nic_search_hint));
                    selectedSearchType = "nicNo";
                    clearDetails();
                    nicNo.setEnabled(false);
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
                        Year = mYear;

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

                search(selectedSearchType);

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

                // New Password Click
                newPasswordChk.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        checkNewpassword(newPasswordChk);
                    }
                });

        // On Click Update Member Details
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validAll())
                {
                    if(!searchMember.getText().toString().equals("") && !searchMember.isEnabled()) {

                        uploadImage();

                        databaseReference = FirebaseDatabase.getInstance().getReference("Member");

                        Member member = new Member();
                        member.setFullName(value[0]);
                        member.setPhoneNo(value[1]);
                        member.setEmail(value[2]);
                        member.setNicNo(value[3]);
                        member.setGender(value[4]);
                        member.setAddress(value[5]);
                        member.setDateOfBirth(value[6]);
                        member.setSportType(value[7]);
                        member.setPassword(value[8]);
                        member.setImage(mImageUri.toString());
                        databaseReference.child("id").setValue(member);

                    }

                }
            }
        });



        return view;
    }

    public String checkNewpassword(CheckBox passwordChk)
    {
        if(passwordChk.isChecked() == true)
        {
            newPassword.setEnabled(true);
            return valid.password(newPassword,getString(R.string.password_errorMessage),getString(R.string.password_errorMessage_Pattern));
        }
        else
        {
            newPassword.getText().clear();
            newPassword.setEnabled(false);
            return  password.getText().toString();
        }
    }

    public boolean validAll()
    {
        int count = 0;
        boolean result = false;

        value[0] = valid.nameField(fullName,getString(R.string.fullName_errorMessage),getString(R.string.fullName_errorMessage_Alphabet));
        value[1] = valid.phoneNumber(phoneNo,getString(R.string.phoneNo_errorMessage),getString(R.string.phoneNo_errorMessage_10_Digit),
                getString(R.string.phoneNo_errorMessage_Start_0));
        value[2] = valid.emaiId(emailId,getString(R.string.email_errorMessage),getString(R.string.email_errorMessage_Valid),getString(R.string.email_errorMessage_Exist));
        value[3] = valid.nicNumber(nicNo,getString(R.string.nic_errorMessage),getString(R.string.nic_errorMessage_valid),getString(R.string.nic_errorMessage_exist));
        value[4] = valid.selectGender(gender,maleRad,femaleRad,genderError,getString(R.string.select_gender_errorMessage));
        value[5] = valid.emptyField(address,getString(R.string.address_errorMessage));
        value[6] = valid.selectDOB(selectedDate,dobError,Year,getString(R.string.dob_errorMessage1),getString(R.string.dob_errorMessage2));
        value[7] = valid.selectSportType(cricketChk,footballChk,volleyballChk,sportTypeError,getString(R.string.sportType_errorMessage));
        value[8] = valid.password(newPassword,getString(R.string.password_errorMessage),getString(R.string.password_errorMessage_Pattern));

//        value[10] = "Activate";
        result = valid.checkImage(memberImage);

        for(int i = 0; i < value.length;i++)
        {
            if(value[i] != "")
            {
                count++;
            }
        }

        if(count == 9)
        {
            if(result)
            {
                return true;
            }
            //Toast.makeText(getContext(),getString(R.string.image_errorMessage), LENGTH_LONG).show();
            StyleableToast.makeText(getContext(),getString(R.string.image_errorMessage),R.style.errorToast).show();
        }
        return false;
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

        memberId.setText(getString(R.string.member_idS_title));
        memberImage.setImageDrawable(null);
        gender.clearCheck();
        fullName.getText().clear();
        phoneNo.getText().clear();
        emailId.getText().clear();
        nicNo.getText().clear();
        nicNo.setEnabled(true);
        address.getText().clear();
        selectedDate.setText("DD/MM/YYYY");
        cricketChk.setChecked(false);
        volleyballChk.setChecked(false);
        footballChk.setChecked(false);
        newPassword.getText().clear();
        password.setText("");
        newPassword.setEnabled(false);
        newPasswordChk.setChecked(false);
    }

    public void getGender(String value)
    {
        if(value.equals("Male"))
        {
            maleRad.setChecked(true);
        }
        if(value.equals("Female"))
        {
            femaleRad.setChecked(true);
        }
    }

    public void search(String searchType)
    {
        if(!searchMember.getText().toString().equals("")) {

            databaseReference = FirebaseDatabase.getInstance().getReference("Member");

            databaseReference.orderByChild(searchType).equalTo(searchMember.getText().toString().toUpperCase()).addValueEventListener(new ValueEventListener()
            {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {

                        ArrayList<Member> list = new ArrayList<>();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Member user = postSnapshot.getValue(Member.class);
                            list.add(user);
                        }

                        memberId.setText(list.get(0).getId());
                        searchMember.setEnabled(false);
                        fullName.setText(list.get(0).getFullName());
                        phoneNo.setText(list.get(0).getPhoneNo());
                        emailId.setText(list.get(0).getEmail());
                        nicNo.setText(list.get(0).getNicNo());
                        address.setText(list.get(0).getAddress());
                        password.setText(list.get(0).getPassword().toString());
                        Picasso.get().load(list.get(0).getImage()).into(memberImage);
                        selectedDate.setText(list.get(0).getDateOfBirth());
                        getGender(list.get(0).getGender());
                        valid.viewSportType(list.get(0).getSportType(),footballChk,cricketChk,volleyballChk);

                    }
                    else
                    {
                        searchMember.setError("Invalid Search Value");
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
            searchMember.setError("Search Field is Empty");
        }

    }

    ProgressDialog progressDialog;
    private void uploadImage()
    {
        if(mImageUri != null)
        {
            // To show progress dialog
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            storageReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String uploadId = databaseReference.push().getKey();
                            String member = uri.toString();
                            databaseReference.child(uploadId).setValue(member);
                        }
                    });

                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Uploaded Success",Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded"+(int)progress+"%");
                        }
                    });


        }
    }

}
