package com.example.hunterz.ui.pay_fee;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.hunterz.DatabaseHandler;
import com.example.hunterz.R;
import com.example.hunterz.Validation;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class PayFeeFragment extends Fragment {

    private PayFeeViewModel payFeeViewModel;
    private Spinner searchType,selectMonth;
    private CircleImageView memberImage;
    private EditText searchTxt,amountTxt;
    private TextView memberName,memberEmail,memberPhone,memberNic,monthError;
    private Button payBtn,searchBtn,clearBtn;
    private RelativeLayout relativeLayout;
    private String selectedSearchType,memberID,selectedMonth;
    private ProgressBar progressBar;


    Validation valid = new Validation(getContext());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        payFeeViewModel = ViewModelProviders.of(this).get(PayFeeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_pay_fee, container, false);

        memberImage = view.findViewById(R.id.profile_pic);
        searchTxt = view.findViewById(R.id.search_txt);
        amountTxt = view.findViewById(R.id.amount_txt);
        memberName = view.findViewById(R.id.profile_name);
        memberEmail = view.findViewById(R.id.profile_email);
        memberPhone = view.findViewById(R.id.member_phone);
        memberNic = view.findViewById(R.id.member_nic);
        payBtn = view.findViewById(R.id.pay_btn);
        searchBtn = view.findViewById(R.id.search_btn);
        clearBtn = view.findViewById(R.id.clear_btn);
        relativeLayout = view.findViewById(R.id.searchlayout);
        progressBar = view.findViewById(R.id.progressBar1);
        monthError = view.findViewById(R.id.month_error);

        generateID("PAY","select payment_id from payment_Table");

        // Drop down list (Search Type)
        searchType = view.findViewById(R.id.select_search);
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(getActivity(),
                R.array.types_array, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchType.setAdapter(adapterType);

        // Select Search Type
        searchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString().trim();
                Toast.makeText(getContext(),value,Toast.LENGTH_SHORT).show();
                if(value.equals("Register Number"))
                {
                    searchTxt.setHint(getString(R.string.register_search_hint));
                    selectedSearchType = "id";
                    clearDetails();
                }
                else if(value.equals("NIC Number"))
                {
                    searchTxt.setHint(getString(R.string.nic_search_hint));
                    selectedSearchType = "nicNo";
                    clearDetails();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Drop down list (Select Month)
        selectMonth = view.findViewById(R.id.select_month);
        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(getActivity(),
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectMonth.setAdapter(adapterMonth);

        // Select Search Type
        selectMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedMonth = parent.getItemAtPosition(position).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Click on search button
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                search = searchTxt.getText().toString();
                if(!searchTxt.getText().toString().equals(""))
                {
                    progressBar.setVisibility(View.VISIBLE);
                    search(selectedSearchType);
                }
                else
                {
                    searchTxt.setError("Search Field is Empty");
                }

            }
        });

        // Click on pay amount button
        payBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addPayment();
            }
        });

        //Click on Clear button
        clearBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clearDetails();
            }
        });

        return view;
    }


    public void search(String searchType)
    {
        DatabaseHandler db=new DatabaseHandler(getContext());
        Cursor res =null;

        if(!searchTxt.getText().toString().equals(""))
        {
            if(searchType.equals("id"))
            {
                res = db.getMember("SELECT * FROM member_Table,authentication_Table WHERE member_Table.authentication_id = authentication_Table.authentication_id AND member_Table.member_id='" + searchTxt.getText().toString().toUpperCase() + "'");
            }
            if(searchType.equals("nicNo"))
            {
                res = db.getMember("SELECT * FROM member_Table,authentication_Table WHERE member_Table.authentication_id = authentication_Table.authentication_id AND member_Table.member_nic='" + searchTxt.getText().toString().toUpperCase() + "'");
            }
            if(res.moveToFirst())
            {
                memberID = res.getString(0);
                searchTxt.setEnabled(false);
                memberName.setText(res.getString(1));
                memberPhone.setText(res.getString(4));
                memberEmail.setText(res.getString(13));
                memberNic.setText(res.getString(5));
                memberImage.setImageBitmap(printImage(res.getBlob(9)));
                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                searchTxt.setError("Invalid Search Value");
            }
        }
        else
        {
            searchTxt.setError("Search Field is Empty");
        }

    }

    private Bitmap printImage(byte[] imageValue)  // To print the image. Convert byte to Bitmap
    {
        byte[] image = imageValue;
        Bitmap bmImage = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bmImage;

    }


    public void clearDetails()
    {
        relativeLayout.setVisibility(View.GONE);
        searchTxt.setEnabled(true);
        searchTxt.getText().clear();
        memberName.setText("");
        memberPhone.setText("");
        memberEmail.setText("");
        memberNic.setText("");
        amountTxt.getText().clear();
        selectMonth.setSelection(0);
        memberImage.setImageDrawable(null);
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

    public void addPayment()
    {
        DatabaseHandler dbHandler = new DatabaseHandler(getContext());

        Double amount = 0.0;
        String[] value = new String[4];
        int count = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        simpleDateFormat.format(new Date());

        value[0] = generateID("PAY","select payment_id from payment_Table");
        value[1] = memberID;
        value[2] = valid.payMonth(monthError,selectedMonth,getString(R.string.month_errorMessage));
        amount = valid.offValue(amountTxt,getString(R.string.amount_errorMessage1),getString(R.string.amount_errorMessage2),getString(R.string.amount_errorMessage3));
        value[3] = simpleDateFormat.format(new Date());

        for(int i = 0; i < value.length;i++)
        {
            if(value[i] != "")
            {
                count++;
            }
        }

        if(count == 4)
        {
            if(amount != 0.0)
            {
                boolean res = dbHandler.insertPayemnt(value[0],value[2],value[3],amount,memberID.toUpperCase()); // Insert Method

                if(res == true) {
                    Toast.makeText(getContext(),"Successfully Paid!", Toast.LENGTH_LONG).show();
                    clearDetails(); // Clear all input field and image view
                    generateID("PAY","select payment_id from payment_Table");
                } else {
                    Toast.makeText(getContext(),"Error While Adding", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}