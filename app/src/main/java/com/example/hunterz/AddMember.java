package com.example.hunterz;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.theartofdev.edmodo.cropper.CropImage;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class AddMember extends Fragment {

    private Button datePicker_Btn,addImageBtn,register_Btn;
    private TextView selectedDate,memberId,genderError,sportTypeError,dobError;
    private EditText fullName, phoneNo, emailId, nicNo, address, password,conPassword;
    private RadioGroup gender;
    private RadioButton maleRad,femaleRad;
    private CheckBox cricketChk,footballChk,volleyballChk;
    private ImageView memberImage;

    private Uri mImageUri; // Image URI

    Calendar calendar;  // Calender
    DatePickerDialog datePickerDialog; // Date Picker
    int Year;

    DatabaseHandler db = new DatabaseHandler(getContext());

    String[] value = new String[12];
    Validation valid = new Validation(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_member, container, false);

        datePicker_Btn = view.findViewById(R.id.date_picker_btn);
        selectedDate = view.findViewById(R.id.selected_date);

        register_Btn = view.findViewById(R.id.register_btn);
        memberId = view.findViewById(R.id.member_id);
        fullName = view.findViewById(R.id.fullName_txt);
        phoneNo = view.findViewById(R.id.phoneNo_txt);
        emailId = view.findViewById(R.id.email_txt);
        nicNo = view.findViewById(R.id.nic_txt);
        address = view.findViewById(R.id.address_txt);
        password = view.findViewById(R.id.password_txt);
        conPassword = view.findViewById(R.id.conPassword_txt);
        gender = view.findViewById(R.id.radioGroupGender);
        maleRad = view.findViewById(R.id.male_rad);
        femaleRad = view.findViewById(R.id.female_rad);
        cricketChk = view.findViewById(R.id.cricket_chk);
        footballChk = view.findViewById(R.id.football_chk);
        volleyballChk = view.findViewById(R.id.volleyball_chk1);
        memberImage = view.findViewById(R.id.member_image);
        addImageBtn = view.findViewById(R.id.add_image_btn);
        genderError = view.findViewById(R.id.gender_error);
        sportTypeError = view.findViewById(R.id.sport_type_error);
        dobError = view.findViewById(R.id.dob_error);

        // Show member Id
        memberId.setText(generateID("HUN","select member_id from member_Table"));

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

        // On Click Add Member Image
        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseFile(v);
            }
        });


        // On Click Register Member
        register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbHandler = new DatabaseHandler(getContext());
                if (validAll()) {

                    ByteArrayOutputStream stream=new ByteArrayOutputStream();
                    String authId = generateID("AUN","select authentication_id from authentication_Table");

                    selectedImage.compress(Bitmap.CompressFormat.PNG,100,stream);
                    byte[] byteArray=stream.toByteArray(); // To get the Image
                    long sizeOfImage = byteArray.length;

                    if((sizeOfImage  / 1024) < 1024)
                    {
                        boolean res = dbHandler.insertMember(value[0],value[1],value[6],value[7],value[2],value[4],value[8],value[5],value[11],byteArray,
                                "admin001",authId,value[3],value[9],"Member"); // Insert Method

                        if(res == true) {
                            Toast.makeText(getContext(),"Successfully Added!", Toast.LENGTH_LONG).show();
                            clearDetails(); // Clear all input field and image view
                            memberId.setText(generateID("LCC","select member_id from member_Table")); // update the Id after adding an item
                        } else {
                            Toast.makeText(getContext(),"Error While Adding", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Image Is Too Large!", Toast.LENGTH_LONG).show();
                    }
                }
            }

        });

        return view;
    }

    public boolean validAll()
    {
        int count = 0;
        boolean result = false;

        value[0] = generateID("HUN","select member_id from member_Table");
        value[1] = valid.nameField(fullName,getString(R.string.fullName_errorMessage),getString(R.string.fullName_errorMessage_Alphabet));
        value[2] = valid.phoneNumber(phoneNo,getString(R.string.phoneNo_errorMessage),getString(R.string.phoneNo_errorMessage_10_Digit),
                getString(R.string.phoneNo_errorMessage_Start_0));
        value[3] = valid.emailIdCheck(emailId,getString(R.string.email_errorMessage),getString(R.string.email_errorMessage_Valid)
                ,getString(R.string.email_errorMessage_Exist),checkIfEmail(emailId.getText().toString()));
        value[4] = valid.nicNumber(nicNo,getString(R.string.nic_errorMessage),getString(R.string.nic_errorMessage_valid),
                getString(R.string.nic_errorMessage_exist),checkIfNic(nicNo.getText().toString())).toUpperCase();
        value[5] = valid.selectGender(gender,maleRad,femaleRad,genderError,getString(R.string.select_gender_errorMessage));
        value[6] = valid.emptyField(address,getString(R.string.address_errorMessage));
        value[7] = valid.selectDOB(selectedDate,dobError,Year,getString(R.string.dob_errorMessage1),getString(R.string.dob_errorMessage2));
        value[8] = valid.selectSportType(cricketChk,footballChk,volleyballChk,sportTypeError,getString(R.string.sportType_errorMessage));
        value[9] = valid.password(password,getString(R.string.password_errorMessage),getString(R.string.password_errorMessage_Pattern));
        value[10] = valid.confirmPassword(conPassword,getString(R.string.confirm_password_errorMessage),getString(R.string.confirm_password_errorMessage_Match),value[9]);
        value[11] = "Activate";
        result = valid.checkImage(memberImage);

        for(int i = 0; i < value.length;i++)
        {
            if(value[i] != "")
            {
                count++;
            }
        }

        if(count == 12)
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
        CropImage.activity().start(getContext(),AddMember.this);
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
//                    memberImage.setImageURI(mImageUri);

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
        conPassword.getText().clear();
        memberImage.setImageDrawable(null);

        emailId.setError(null);
    }


    public String generateID(String id, String query) // Auto Generate ID
    {
        DatabaseHandler db = new DatabaseHandler(getContext());

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
