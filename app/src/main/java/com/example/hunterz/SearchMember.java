package com.example.hunterz;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
    private String selectedSearchType,authenticationID;
    private Uri mImageUri;

//    private FirebaseAuth mAuth;
//    DatabaseReference databaseReference;
//    StorageReference storageReference;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year;

    String[] value = new String[9];
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

                String id = memberId.getText().toString();

                if(!searchMember.getText().toString().equals("") && !searchMember.isEnabled())
                {
                    if (validAll())
                    {
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        DatabaseHandler db = new DatabaseHandler(getContext());

                        selectedImage.compress(Bitmap.CompressFormat.PNG,100,stream);
                        byte[] byteArray=stream.toByteArray(); // To get the Image

                        boolean result = db.editMember(searchMember.getText().toString(),value[0],value[5],value[6],value[1],value[3],value[7],
                                value[4],byteArray,value[2],password.getText().toString(),authenticationID);

                        if(result == true)
                        {
                            Toast.makeText(getContext(),"Successfully Updated!", LENGTH_LONG).show();
                            clearDetails(); // Clear all input field and image view
                        } else
                            {
                                Toast.makeText(getContext(),"Error While Updating", LENGTH_LONG).show();
                        }
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
        int count = 0,length = 8;
        boolean result = false;

        value[0] = valid.nameField(fullName,getString(R.string.fullName_errorMessage),getString(R.string.fullName_errorMessage_Alphabet));
        value[1] = valid.phoneNumber(phoneNo,getString(R.string.phoneNo_errorMessage),getString(R.string.phoneNo_errorMessage_10_Digit),
                getString(R.string.phoneNo_errorMessage_Start_0));
        value[2] = valid.emailIdCheckUpdate(emailId,getString(R.string.email_errorMessage),getString(R.string.email_errorMessage_Valid)
                ,getString(R.string.email_errorMessage_Exist),
                checkIfEmail(emailId.getText().toString()),getUpdateEmail());
        value[3] = valid.nicNumberUpdate(nicNo,getString(R.string.nic_errorMessage),getString(R.string.nic_errorMessage_valid),
                getString(R.string.nic_errorMessage_exist),
                checkIfNic(nicNo.getText().toString()),getUpdateNic()).toUpperCase();
        value[4] = valid.selectGender(gender,maleRad,femaleRad,genderError,getString(R.string.select_gender_errorMessage));
        value[5] = valid.emptyField(address,getString(R.string.address_errorMessage));
        value[6] = valid.selectDOB(selectedDate,dobError,Year,getString(R.string.dob_errorMessage1),getString(R.string.dob_errorMessage2));
        value[7] = valid.selectSportType(cricketChk,footballChk,volleyballChk,sportTypeError,getString(R.string.sportType_errorMessage));

        if(newPasswordChk.isChecked()) {
            length = 9;
            value[8] = valid.passwordUpdate(newPassword,password,getString(R.string.password_errorMessage), getString(R.string.password_errorMessage_Pattern));
        }
        result = valid.checkImage(memberImage);

        for(int i = 0; i < value.length;i++)
        {
            if(value[i] != "")
            {
                count++;
            }
        }

        if(count == length)
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

        emailId.setError(null);
        nicNo.setError(null);
    }


    public boolean getUpdateEmail()
    {
        DatabaseHandler db=new DatabaseHandler(getContext());

        Cursor res = db.getMember("SELECT authentication_id FROM member_Table WHERE member_Table.member_id='" + searchMember.getText().toString().toUpperCase() + "'");

        String id="";

        if(res.moveToFirst()) {

            id = res.getString(0);
        }

        if(authenticationID.equals(id))
        {
            return true;
        }
        return false;
    }

    public boolean getUpdateNic()
    {
        DatabaseHandler db=new DatabaseHandler(getContext());

        Cursor res = db.getMember("SELECT authentication_id FROM member_Table WHERE member_Table.member_nic='" + searchMember.getText().toString().toUpperCase() + "'");
        String id="";

        if(res.moveToFirst()) {

           id = res.getString(0);
        }

        if(authenticationID.equals(id))
        {
            return true;
        }
        return false;
    }


    public void search(String searchType)
    {
        DatabaseHandler db=new DatabaseHandler(getContext());
        Cursor res =null;

        if(!searchMember.getText().toString().equals(""))
        {
            if(searchType.equals("id"))
            {
                res = db.getMember("SELECT * FROM member_Table,authentication_Table WHERE member_Table.authentication_id = authentication_Table.authentication_id AND member_Table.member_id='" + searchMember.getText().toString().toUpperCase() + "'");
                nicNo.setEnabled(true);
            }
            if(searchType.equals("nicNo"))
            {
                res = db.getMember("SELECT * FROM member_Table,authentication_Table WHERE member_Table.authentication_id = authentication_Table.authentication_id AND member_Table.member_nic='" + searchMember.getText().toString().toUpperCase() + "'");
                nicNo.setEnabled(false);
            }
            if(res.moveToFirst())
            {
                memberId.setText(res.getString(0));
                searchMember.setEnabled(false);
                fullName.setText(res.getString(1));
                phoneNo.setText(res.getString(4));
                emailId.setText(res.getString(13));
                nicNo.setText(res.getString(5));
                address.setText(res.getString(2));
                password.setText(res.getString(14));
                memberImage.setImageBitmap(printImage(res.getBlob(9)));
                selectedDate.setText(res.getString(3));
                valid.getGender(res.getString(7),maleRad,femaleRad);
                valid.viewSportType(res.getString(6),footballChk,cricketChk,volleyballChk);
                authenticationID = res.getString(11); // authentication Id
            }
            else
            {
                searchMember.setError("Invalid Search Value");
            }
        }
        else
        {
            searchMember.setError("Search Field is Empty");
        }

    }

    private Bitmap printImage(byte[] imageValue)  // To print the image. Convert byte to Bitmap
    {
        byte[] image = imageValue;
        Bitmap bmImage = BitmapFactory.decodeByteArray(image,0,image.length);
        return bmImage;
    }

    Bitmap selectedImage;
    public void onActivityResult(int reqCode, int resultCode, Intent data) // To crop and select the Image
    {
        super.onActivityResult(reqCode,resultCode,data);

        if(reqCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK)
            {
                try
                {
                    mImageUri = result.getUri();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(mImageUri);
                    selectedImage = BitmapFactory.decodeStream(imageStream);
                    memberImage.setImageBitmap(selectedImage);

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

    public boolean checkIfEmail(String email)  // Checking email id whether already exist
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());

        Cursor cursor = dbHandler.getEmailExist();

        String existEmail;

        if (cursor.moveToFirst()) {
            do {
                existEmail = cursor.getString(0); // getting email from database

                if (existEmail.equals(email)) { // checking whether email is exist
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    public boolean checkIfNic(String email)  // Checking Nic whether already exist
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());

        Cursor cursor = dbHandler.getNicExist();

        String existEmail;

        if (cursor.moveToFirst()) {
            do {
                existEmail = cursor.getString(0); // getting email from database

                if (existEmail.equals(email)) { // checking whether email is exist
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

}
