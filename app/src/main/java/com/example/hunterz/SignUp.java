package com.example.hunterz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends AppCompatActivity {

    private TextView signIn_Btn;
    private EditText fullName,phoneNo,emaiId,nicNumber,password,conPassword;
    private Button register_Btn;

    Validation valid = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signIn_Btn = findViewById(R.id.signIn_txt_btn);
        fullName = findViewById(R.id.fullName_txt);
        phoneNo = findViewById(R.id.phoneNo_txt);
        emaiId = findViewById(R.id.email_txt);
        nicNumber = findViewById(R.id.nic_txt);
        password = findViewById(R.id.password_txt);
        conPassword = findViewById(R.id.conPassword_txt);
        register_Btn = findViewById(R.id.register_btn);

        // Click SIGN IN Button
        signIn_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        // Click REGISTER Button
        register_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRegisterData();
            }
        });
    }

    public void openLogin()
    {
        Intent intent = new Intent(this,SignIn.class);
        startActivity(intent);
        finish(); // to stop the back option
    }

    public void getRegisterData()
    {
        String[] value = new String[6];

        value[0] = valid.nameField(fullName,getString(R.string.fullName_errorMessage),getString(R.string.fullName_errorMessage_Alphabet));
        value[1] = valid.phoneNumber(phoneNo,getString(R.string.phoneNo_errorMessage),getString(R.string.phoneNo_errorMessage_10_Digit),
                getString(R.string.phoneNo_errorMessage_Start_0));
//      value[2] = valid.emaiId(emaiId,getString(R.string.email_errorMessage),getString(R.string.email_errorMessage_Valid)
////                ,getString(R.string.email_errorMessage_Exist),checkIfExists(emailID.getText().toString()));
        value[3] = valid.nicNumber(nicNumber,getString(R.string.nic_errorMessage),getString(R.string.nic_errorMessage_valid));
        value[4] = valid.password(password,getString(R.string.password_errorMessage),getString(R.string.password_errorMessage_Pattern));
        value[5] = valid.confirmPassword(conPassword,getString(R.string.confirm_password_errorMessage),getString(R.string.confirm_password_errorMessage_Match),value[4]);
    }


}
