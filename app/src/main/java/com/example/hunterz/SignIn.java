package com.example.hunterz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignIn extends AppCompatActivity {

    private Button signUp_btn,signIn_btn;
    private EditText password,username;

    static String MemberID;

    Validation valid = new Validation(this);

    DatabaseHandler dbHandle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        dbHandle = new DatabaseHandler(this);

        signUp_btn = findViewById(R.id.signUp_btn);
        signIn_btn = findViewById(R.id.signIn_btn);
        password = findViewById(R.id.passWord_txt);
        username = findViewById(R.id.userName_txt);

      //  mAuth = FirebaseAuth.getInstance();

        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName,passWord;
                Cursor cursor = null;

                userName = valid.emptyField(username, getString(R.string.userName_errorMessage));
                passWord = valid.emptyField(password, getString(R.string.passWord_errorMessage));

                if(!userName.equals("") && !passWord.equals(""))
                {
                    cursor = dbHandle.getAuthentication("SELECT user_role,authentication_id FROM authentication_Table WHERE username='" + userName + "' AND password ='" + passWord + "'");

                    if(cursor.moveToFirst())
                    {
                        String userRole,authenticationId,status;
                        userRole = cursor.getString(0);
                        authenticationId = cursor.getString(1);


                        if(userRole.equals("Admin"))
                        {
                            openAdminHome();
                        }
                        else if(userRole.equals("Member"))
                        {
                            Cursor res = dbHandle.getMember("SELECT member_id,member_status FROM member_Table WHERE authentication_id ='" + authenticationId + "'");

                            if(res.moveToFirst())
                            {
                                status = res.getString(1);

                                if(status.equals("Activate"))
                                {
                                    MemberID = res.getString(0);
                                    openMemberHome();
                                }
                                else
                                {
                                    Toast.makeText(SignIn.this,"Account Is Blocked!",Toast.LENGTH_LONG).show();
                                    username.getText().clear();
                                    password.getText().clear();
                                    username.setFocusable(true);
                                }
                            }
//                            res.close();
                        }
                    }
                    else
                    {
                        Toast.makeText(SignIn.this,"Incorrect Username Or Password",Toast.LENGTH_LONG).show();
                    }
                }

//                cursor.close();

            }
        });

        // Click On Sign Up Button
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

    }

    public void openRegister()
    {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
        finish();
    }

    public void openAdminHome()
    {
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
        finish();
    }

    public void openMemberHome()
    {
        Intent intent = new Intent(this,MemberHome.class);
        startActivity(intent);
        finish();
    }

}
