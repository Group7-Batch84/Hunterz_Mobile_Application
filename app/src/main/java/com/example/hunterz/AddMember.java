package com.example.hunterz;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.theartofdev.edmodo.cropper.CropImage;

import org.w3c.dom.Text;

import java.security.Provider;
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

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    String[] value = new String[12];
    Validation valid = new Validation();

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
        volleyballChk = view.findViewById(R.id.volleyball_chk);
        memberImage = view.findViewById(R.id.member_image);
        addImageBtn = view.findViewById(R.id.add_image_btn);
        genderError = view.findViewById(R.id.gender_error);
        sportTypeError = view.findViewById(R.id.sport_type_error);
        dobError = view.findViewById(R.id.dob_error);

        mAuth = FirebaseAuth.getInstance();

        // Database Firebase (Storage / Database)
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile_Image/"+ UUID.randomUUID().toString()); // Image Storage Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Member");

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
                if (validAll())
                {
                    uploadImage();
                    mAuth.createUserWithEmailAndPassword(emailId.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("Success", "createUserWithEmail:success");
                                        clearDetails();


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("Not Success", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }

            }
        });


        return view;
    }

    public boolean validAll()
    {
        int count = 0;
        boolean result = false;

        value[0] = "HUN001";
        value[1] = valid.nameField(fullName,getString(R.string.fullName_errorMessage),getString(R.string.fullName_errorMessage_Alphabet));
        value[2] = valid.phoneNumber(phoneNo,getString(R.string.phoneNo_errorMessage),getString(R.string.phoneNo_errorMessage_10_Digit),
                getString(R.string.phoneNo_errorMessage_Start_0));
        value[3] = valid.emaiId(emailId,getString(R.string.email_errorMessage),getString(R.string.email_errorMessage_Valid),getString(R.string.email_errorMessage_Exist));
        value[4] = valid.nicNumber(nicNo,getString(R.string.nic_errorMessage),getString(R.string.nic_errorMessage_valid),getString(R.string.nic_errorMessage_exist));
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
                    makeText(getContext(), getString(R.string.member_image_errorMessage1), LENGTH_LONG).show();
                }
            }
            else {
                makeText(getContext(), getString(R.string.member_image_errorMessage2), LENGTH_LONG).show();
            }
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
                            Member member = new Member(value[0],value[1],value[2],value[3],value[4],value[5],value[6],value[7],value[8],value[9],value[11],uri.toString());
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
    }

    public String checkEmail(String email)
    {
        String result = "";
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                boolean check = !task.getResult().getSignInMethods().isEmpty();

                if(check)
                {
                    emailId.setError("Email Id is Already Exist");

                }
                else
                {

                }
            }

        });

        return "";
    }

}
