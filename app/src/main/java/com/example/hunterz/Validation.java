package com.example.hunterz;

import android.widget.EditText;

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

    public String emaiId(EditText textField,String errorMessage1,String errorMessage2,String errorMessage3,boolean ifExists)
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


    public String nicNumber(EditText textField,String errorMessage1,String errorMessage2)
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
            return nicNo;
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
}
