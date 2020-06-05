package com.example.hunterz;

import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

    public String emailIdCheck(EditText textField,String errorMessage1,String errorMessage2,String errorMessage3,boolean ifExists)
    {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailId = textField.getText().toString();

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
            if(ifExists) // checking whether email is exist
            {
                textField.setError(errorMessage3);
                return "";
            }
            else
            {
                return emailId;
            }
        }
    }

    public void getGender(String value,RadioButton maleRad,RadioButton femaleRad)
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

    public String nicNumber(EditText textField, String errorMessage1, String errorMessage2,String errorMessage3,boolean ifExists)
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
            if(ifExists) // checking whether email is exist
            {
                textField.setError(errorMessage3);
                return "";
            }
            else
            {
                return nicNo;
            }
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
            else if(amount < 1500)
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

    public String payMonth(TextView errorText,String month,String errorMessage)
    {
        if(month.equals(""))
        {
            errorText.setText(errorMessage);
            return "";
        }
        else if(month.equals("Month"))
        {
            errorText.setText(errorMessage);
            return "";
        }
        else
        {
            errorText.setText("");
            return month;
        }
    }


    //  Update Validation

    public String emailIdCheckUpdate(EditText textField,String errorMessage1,String errorMessage2,String errorMessage3,boolean ifExists,
                                     boolean getUpdate)
    {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailId = textField.getText().toString();

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
            if(ifExists) // checking whether email is exist
            {
                if(getUpdate)
                {
                    return emailId;
                }
                else {
                    textField.setError(errorMessage3);
                    return "";
                }
            }
            else
            {
                return emailId;
            }
        }
    }


    public String nicNumberUpdate(EditText textField, String errorMessage1, String errorMessage2,String errorMessage3,boolean ifExists,
                                  boolean getUpdate)
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
            if(ifExists) // checking whether nic is exist
            {
                if(getUpdate)
                {
                    return nicNo;
                }
                else {
                    textField.setError(errorMessage3);
                    return "";
                }

            }
            else
            {
                return nicNo;
            }
        }
    }

    public String passwordUpdate(EditText textField,TextView textView,String errorMessage1,String errorMessage2)
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
            textView.setText(passWord);
            return passWord;
        }
    }

    // Post Validation

    public String getPostSubject(EditText textField,String errorMessage1,String errorMessage2,int letter)
    {
        String postSubject = textField.getText().toString();
        int count = 0;

        for (int i = 0; i < postSubject.length();i++)
        {
            if(postSubject.charAt(i) != ' ') {
                count++;
            }
        }

        if(postSubject.isEmpty())
        {
            textField.setError(errorMessage1);
            return "";
        }
        else if(count > letter)
        {
            textField.setError(errorMessage2);
            return "";
        }
        else
        {
            return postSubject;
        }
    }

}
