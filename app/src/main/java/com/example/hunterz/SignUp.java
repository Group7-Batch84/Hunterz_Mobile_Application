package com.example.hunterz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
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

    DatabaseReference databaseReference;
    StorageReference storageReference;

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

        storageReference = FirebaseStorage.getInstance().getReference().child("Profile_Image/"+ UUID.randomUUID().toString()); // Image Storage Reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Pending_Member");

        // Click REGISTER Button
        register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRegisterData())
                {
                    uploadImage();
                    clearDetails();
                    Toast.makeText(SignUp.this,"Successfully Requested",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

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
                    memberImage.setImageURI(mImageUri);

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

        generateID("NEW",3,"Pending_Member","id");
        value[0] = IDS;
        value[1] = valid.nameField(fullName,getString(R.string.fullName_errorMessage),getString(R.string.fullName_errorMessage_Alphabet));
        value[2] = valid.phoneNumber(phoneNo,getString(R.string.phoneNo_errorMessage),getString(R.string.phoneNo_errorMessage_10_Digit),
                getString(R.string.phoneNo_errorMessage_Start_0));
        value[3] = valid.emaiId(emaiId,getString(R.string.email_errorMessage),getString(R.string.email_errorMessage_Valid),
                getString(R.string.email_errorMessage_Exist));
        value[4] = valid.nicNumber(nicNumber,getString(R.string.nic_errorMessage),getString(R.string.nic_errorMessage_valid),getString(R.string.nic_errorMessage_exist));
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


    public void generateID(final String id,final int length,String table,String column) // Auto Generate ID
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

    ProgressDialog progressDialog;
    private void uploadImage()
    {
        if(mImageUri != null)
        {
            // To show progress dialog
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            storageReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Member member = new Member(value[0],value[1],value[2],value[3],value[4],value[5],value[6],value[7],value[8],value[9],uri.toString());
                            databaseReference.child(IDS).setValue(member);
                        }
                    });

                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this,"Uploaded Success",Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+progress+"%");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(SignUp.this,"Failed",Toast.LENGTH_SHORT).show();
                        }
                    });

        }
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

}
