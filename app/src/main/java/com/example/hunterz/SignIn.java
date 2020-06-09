package com.example.hunterz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class SignIn extends AppCompatActivity {

    private Button signUp_btn,signIn_btn;
    private EditText password,username;

   // private FirebaseAuth mAuth;

    Validation valid = new Validation(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUp_btn = findViewById(R.id.signUp_btn);
        signIn_btn = findViewById(R.id.signIn_btn);
        password = findViewById(R.id.passWord_txt);
        username = findViewById(R.id.userName_txt);

      //  mAuth = FirebaseAuth.getInstance();

        signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                valid.emptyField(username, getString(R.string.userName_errorMessage));
                valid.emptyField(password, getString(R.string.passWord_errorMessage));

                openHome();
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

    public void openHome()
    {
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
        finish();
    }

}
