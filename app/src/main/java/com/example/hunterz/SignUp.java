package com.example.hunterz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class SignUp extends AppCompatActivity {

    private TextView signIn_Btn,genderError,sportTypeError,dobError,selectedDate;
    private EditText fullName,phoneNo,emaiId,nicNumber,password,conPassword,address;
    private Button register_Btn,addImageBtn,datePicker_Btn;
    private RadioGroup gender;
    private RadioButton maleRad,femaleRad;
    private CheckBox cricketChk,footballChk,volleyballChk;
    private ImageView memberImage;
    private ProgressBar progressBar;

    private Uri mImageUri; // Image URI
    private String IDS;

    Calendar calendar;  // Calender
    DatePickerDialog datePickerDialog; // Date Picker
    int Year;

    String[] value = new String[11];

    Validation valid = new Validation();
    DatabaseHandler dbHandle = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signIn_Btn = findViewById(R.id.signIn_txt_btn);
        fullName = findViewById(R.id.fullName_txt);
        phoneNo = findViewById(R.id.phoneNo_txt);
        emaiId = findViewById(R.id.email_txt);
        nicNumber = findViewById(R.id.nic_txt);
        address = findViewById(R.id.address_txt);
        password = findViewById(R.id.password_txt);
        conPassword = findViewById(R.id.conPassword_txt);
        register_Btn = findViewById(R.id.register_btn);
        gender = findViewById(R.id.radioGroupGender);
        maleRad = findViewById(R.id.male_rad);
        femaleRad = findViewById(R.id.female_rad);
        cricketChk = findViewById(R.id.cricket_chk);
        footballChk = findViewById(R.id.football_chk);
        volleyballChk = findViewById(R.id.volleyball_chk1);
        memberImage = findViewById(R.id.member_image);
        addImageBtn = findViewById(R.id.add_image_btn);
        genderError = findViewById(R.id.gender_error);
        sportTypeError = findViewById(R.id.sport_type_error);
        dobError = findViewById(R.id.dob_error);
        selectedDate = findViewById(R.id.selected_date);
        datePicker_Btn = findViewById(R.id.date_picker_btn);
        progressBar = findViewById(R.id.progressBar1);

        generateID("NEW","select member_id from pendingMember_Table");

        // Click "Date Picker" Button
        datePicker_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);


                datePickerDialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        Year = mYear;
                        selectedDate.setText(mDay+"/"+(mMonth+1)+"/"+mYear);
                    }
                },day,month,year);
                datePickerDialog.show();
            }
        });




        // Click SIGN IN Button
        signIn_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseFile(v);
            }
        });

        // Click REGISTER Button
        register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler dbHandler = new DatabaseHandler(SignUp.this);
                if (getRegisterData())
                {
                    if(getRegisterData())
                    {
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();

                        selectedImage.compress(Bitmap.CompressFormat.PNG,100,stream);
                        byte[] byteArray=stream.toByteArray(); // To get the Image

                        boolean res = dbHandler.insertPendingMember(value[0],value[1],value[6],value[7],
                                value[2],value[4],value[8],value[5],byteArray, value[3],value[9]);

                        if(res == true) {
                            clearDetails();
                            Toast.makeText(SignUp.this, "Successfully Requested", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignUp.this,"Error While Adding", Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }
        });
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
                    final InputStream imageStream = this.getContentResolver().openInputStream(mImageUri);
                    selectedImage = BitmapFactory.decodeStream(imageStream);
                    memberImage.setImageBitmap(selectedImage);

                } catch (Exception e) {
                    e.printStackTrace();
                    makeText(this, getString(R.string.member_image_errorMessage1), LENGTH_LONG).show();
                }
            }
            else {
                makeText(this, getString(R.string.member_image_errorMessage2), LENGTH_LONG).show();
            }
        }
    }

    public void openLogin()
    {
        Intent intent = new Intent(this,SignIn.class);
        startActivity(intent);
        finish(); // to stop the back option
    }

    public void onChooseFile(View v) // To choose the insert file
    {
        CropImage.activity().start(SignUp.this);
    }

    public boolean getRegisterData()
    {
        int count = 0;
        boolean result = false;

        value[0] = generateID("NEW","select member_id from pendingMember_Table");
        value[1] = valid.nameField(fullName,getString(R.string.fullName_errorMessage),getString(R.string.fullName_errorMessage_Alphabet));
        value[2] = valid.phoneNumber(phoneNo,getString(R.string.phoneNo_errorMessage),getString(R.string.phoneNo_errorMessage_10_Digit),
                getString(R.string.phoneNo_errorMessage_Start_0));
        value[3] = valid.emailIdCheck(emaiId,getString(R.string.email_errorMessage),getString(R.string.email_errorMessage_Valid)
                ,getString(R.string.email_errorMessage_Exist),checkIfEmail(emaiId.getText().toString()));
        value[4] = valid.nicNumber(nicNumber,getString(R.string.nic_errorMessage),getString(R.string.nic_errorMessage_valid),
                getString(R.string.nic_errorMessage_exist),checkIfNic(nicNumber.getText().toString())).toUpperCase();
        value[5] = valid.selectGender(gender,maleRad,femaleRad,genderError,getString(R.string.select_gender_errorMessage));
        value[6] = valid.emptyField(address,getString(R.string.address_errorMessage));
        value[7] = valid.selectDOB(selectedDate,dobError,Year,getString(R.string.dob_errorMessage1),getString(R.string.dob_errorMessage2));
        value[8] = valid.selectSportType(cricketChk,footballChk,volleyballChk,sportTypeError,getString(R.string.sportType_errorMessage));
        value[9] = valid.password(password,getString(R.string.password_errorMessage),getString(R.string.password_errorMessage_Pattern));
        value[10] = valid.confirmPassword(conPassword,getString(R.string.confirm_password_errorMessage),getString(R.string.confirm_password_errorMessage_Match),value[9]);
        result = valid.checkImage(memberImage);


        for(int i = 0; i < value.length;i++)
        {
            if(value[i] != "")
            {
                count++;
            }
        }

        if(count == 11)
        {
            if(result) {
                return true;
            }
            StyleableToast.makeText(this,getString(R.string.image_errorMessage),R.style.errorToast).show();
        }
        return false;
    }


    public String generateID(String id, String query) // Auto Generate ID
    {
        DatabaseHandler db = new DatabaseHandler(this);

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


    public void clearDetails()  // Clear All Data fields
    {
        gender.clearCheck();
        fullName.getText().clear();
        phoneNo.getText().clear();
        emaiId.getText().clear();
        nicNumber.getText().clear();
        address.getText().clear();
        selectedDate.setText("DD/MM/YYYY");
        cricketChk.setChecked(false);
        volleyballChk.setChecked(false);
        footballChk.setChecked(false);
        password.getText().clear();
        memberImage.setImageDrawable(null);
        conPassword.getText().clear();
    }

    public boolean checkIfEmail(String email)  // Checking email id whether already exist
    {
        DatabaseHandler dbHandler = new DatabaseHandler(this);

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
        DatabaseHandler dbHandler = new DatabaseHandler(this);

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
