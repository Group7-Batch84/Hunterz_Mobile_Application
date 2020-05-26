package com.example.hunterz;

import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class Validation {

    public String emptyField(EditText textField, String errorMessage)
    {
        if(textField.getText().toString().isEmpty())
        {
            textField.setError(errorMessage);
            return "";
        }
        return textField.getText().toString();
    }

    public String nameField(EditText textField, String errorMessage1, String errorMessage2)
    {
        String namePattern = "^[a-zA-Z\\s]+$";

        if(textField.getText().toString().isEmpty())
        {
            textField.setError(errorMessage1);
            return "";
        }
        else if(textField.getText().toString().matches(namePattern))
        {
            return textField.getText().toString();
        }
        else
        {
            textField.setError(errorMessage2);
            return "";
        }
    }

    public String phoneNumber(EditText textField,String errorMessage1, String errorMessage2, String errorMessage3)
    {
        String numberPattern = "[0-9]{10}";
        String phoneNo = textField.getText().toString();

        if(phoneNo.isEmpty())
        {
            textField.setError(errorMessage1);
            return "";
        }
        else if(!phoneNo.matches(numberPattern))
        {
            textField.setError(errorMessage2);
            return "";
        }
        else
        {
            if(phoneNo.charAt(0) != '0')
            {
                textField.setError(errorMessage3);
                return "";
            }
            else
            {
                return phoneNo;
            }
        }
    }

    FirebaseAuth mAuth;
    String value = "";
    public String emaiId(final EditText textField, String errorMessage1, String errorMessage2,final String errorMessage3)
    {

        mAuth = FirebaseAuth.getInstance();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        final String emailId = textField.getText().toString();
        String finalValue = "";

        if(emailId.isEmpty())
        {
            textField.setError(errorMessage1);
            return "";
        }
        else if(!emailId.matches(emailPattern)) // checking email pattern
        {
            textField.setError(errorMessage2);
            return "";
        }
        else
        {
            mAuth.fetchSignInMethodsForEmail(emailId).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                    boolean check = !task.getResult().getSignInMethods().isEmpty();

                    if(check)
                    {
                        textField.setError(errorMessage3);
                        value = "";
                    }
                    else
                    {
                        value = emailId;
                    }
                }

            });
                return value;

        }
    }

    DatabaseReference databaseReference;
    String nicValue;
    public String nicNumber(final EditText textField, String errorMessage1, String errorMessage2, final String errorMessage3)
    {
        String nicNo = textField.getText().toString();
        String nicPattern = "[0-9]{9}[x|X|v|V]$";

        if(nicNo.isEmpty())
        {
            textField.setError(errorMessage1);
            return "";
        }
        else if(!nicNo.matches(nicPattern))
        {
            textField.setError(errorMessage2);
            return "";
        }
        else
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Member");

            databaseReference.orderByChild("nicNo").equalTo(nicNo).addValueEventListener(new ValueEventListener()
            {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {

                        textField.setError(errorMessage3);
                        nicValue = "";
                    }
                    else
                    {
                        nicValue = textField.getText().toString();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.e("TAG", "Failed to read user", error.toException());
                }
            });

            return nicValue;
        }
    }

    public String password(EditText textField,String errorMessage1,String errorMessage2)
    {
        String password = "(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%&*()_+=|<>?{}\\[\\]~-]).{8}";
        String passWord = textField.getText().toString();

        if(passWord.isEmpty())
        {
            textField.setError(errorMessage1);
            return "";
        }
        else if(!passWord.matches(password))
        {
            textField.setError(errorMessage2);
            return "";
        }
        else
        {
            return passWord;
        }
    }

    public String confirmPassword(EditText textField,String errorMessage1,String errorMessage2,String password)
    {
        String confirmPassWord = textField.getText().toString();

        if(confirmPassWord.isEmpty())
        {
            textField.setError(errorMessage1);
            return "";
        }
        else if(confirmPassWord.equals(password))
        {
            return confirmPassWord;
        }
        else
        {
            textField.setError(errorMessage2);
            return "";
        }

    }

    public double priceValue(EditText textField,String errorMessage1,String errorMessage2)
    {
        String price = textField.getText().toString();

        if(price.isEmpty())
        {
            textField.setError(errorMessage1);
            return 0;
        }
        else
        {
            double amount = Double.valueOf(price);
            if(amount <= 0)
            {
                textField.setError(errorMessage2);
                return 0;
            }
            else
            {
                return amount;
            }
        }
    }

    public double offValue(EditText textField,String errorMessage1,String errorMessage2,String errorMessage3)
    {
        String price = textField.getText().toString();

        if(price.isEmpty())
        {
            textField.setError(errorMessage1);
            return 0;
        }
        else
        {
            double amount = Double.valueOf(price);
            if(amount <= 0)
            {
                textField.setError(errorMessage2);
                return 0;
            }
            else if(amount >= 100)
            {
                textField.setError(errorMessage3);
                return 0;
            }
            else
            {
                return amount;
            }
        }
    }

    public String selectGender(RadioGroup genderGroupradio, RadioButton male, RadioButton female, TextView errorLabel, String errorMessage1)
    {
        int selected = genderGroupradio.getCheckedRadioButtonId();

        if (selected == -1)
        {
            errorLabel.setText(errorMessage1);
            return "";
        } else
        {
            if (male.isChecked())
            {
                errorLabel.setText("");
                return "Male";
            }
            else if (female.isChecked())
            {
                errorLabel.setText("");
                return "Female";
            }
        }
        return "";
    }

    public String selectDOB(TextView defaultDate,TextView errorLabel,int year,String errorMessage1,String errorMessage2)
    {
        String dob = defaultDate.getText().toString();
        if(dob.equals("DD/MM/YYYY"))
        {
            errorLabel.setText(errorMessage1);
            return "";
        }
        else
        {
            if(2007 <= year)
            {
                errorLabel.setText(errorMessage2);
                return "";
            }
            else
            {
                errorLabel.setText("");
                return dob;
            }
        }
    }

    public String selectSportType(CheckBox cricket,CheckBox football,CheckBox volleyball,TextView errorLabel,String errorMessage)
    {
        ArrayList<String> sportType = new ArrayList<>();
        String selected = "";

        if(cricket.isChecked())
        {
            sportType.add("Cricket ");
        }
        if(football.isChecked())
        {
            sportType.add("Football ");
        }
        if(volleyball.isChecked())
        {
            sportType.add("Volleyball ");
        }
        if((cricket.isChecked() == false && volleyball.isChecked() == false && football.isChecked() == false))
        {
            sportType.clear();
            errorLabel.setText(errorMessage);
            return "";
        }
        else
        {
            for(int i = 0; i < sportType.size(); i++)
            {
                selected = selected + sportType.get(i);
            }
            errorLabel.setText("");
        }
        return selected;
    }

    public boolean checkImage(ImageView image)
    {
        if(image.getDrawable() == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

//    String IDS="";
//    public void generateID(final String id,final int length,String table,String column) // Auto Generate ID
//    {
//
//            databaseReference = FirebaseDatabase.getInstance().getReference(table);
//
//            databaseReference.addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    int count = 0;
//                    String idType = "";
//
//                    ArrayList<Member> list = new ArrayList<>();
//
//                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                        Member user = postSnapshot.getValue(Member.class);
//                        list.add(user);
//                        count++;
//                    }
//
//                    idType = list.get(list.size() - 1).getId().toString();
//
//                    if (count > 0) {
//                        String x = idType.substring(length);
//                        int ID = Integer.parseInt(x);
//
//                        if (ID > 0 && ID < 9) {
//                            ID = ID + 1;
//                            IDS = id + "00" + ID;
//                        } else if (ID >= 9 && ID < 99) {
//                            ID = ID + 1;
//                            IDS = id + "0" + ID;
//                        } else if (ID >= 99) {
//                            ID = ID + 1;
//                            IDS = id + ID;
//                        }
//
//                    } else {
//                        IDS = id + "001";
//                    }
//
//                    Log.d("Value",IDS);
//                }
//
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                    Log.e("TAG", "Failed to read user", error.toException());
//                }
//
//            });
//    }

    public void viewSportType(String sportType,CheckBox football,CheckBox cricket, CheckBox volleyball)
    {
        String[] splitedType = sportType.split(" ");

        for(int i = 0 ; i < splitedType.length; i++)
        {
            if(splitedType[i].equals("Football"))
            {
                football.setChecked(true);
            }
            else if(splitedType[i].equals("Cricket"))
            {
                cricket.setChecked(true);
            }
            else if(splitedType[i].equals("Volleyball"))
            {
                volleyball.setChecked(true);
            }
        }

    }


}
