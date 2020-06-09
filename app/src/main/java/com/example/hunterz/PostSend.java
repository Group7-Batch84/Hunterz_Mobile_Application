package com.example.hunterz;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class PostSend extends Fragment {

    private EditText postSubject,postdescription;
    private Button addImageBtn,sendPostBtn;
    private ImageView postImage;

    private Uri mImageUri; // Image URI

    String[] value = new String[4];

    Validation valid = new Validation(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_send, container, false);

        postSubject = view.findViewById(R.id.post_subject_txt);
        postdescription = view.findViewById(R.id.post_description_txt);
        addImageBtn = view.findViewById(R.id.add_image_btn);
        sendPostBtn = view.findViewById(R.id.send_post_btn);
        postImage = view.findViewById(R.id.post_image);


        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseFile(v);
            }
        });

        // Click On Send Post
        sendPostBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                DatabaseHandler dbHandler = new DatabaseHandler(getContext());
                if (validAll()) {

                    ByteArrayOutputStream stream=new ByteArrayOutputStream();

                    selectedImage.compress(Bitmap.CompressFormat.PNG,100,stream);
                    byte[] byteArray=stream.toByteArray(); // To get the Image
                    long sizeOfImage = byteArray.length;

                    if((sizeOfImage  / 1024) < 1024)
                    {
                        boolean res = dbHandler.insertPost(value[0], value[3],value[1],value[2],byteArray,"admin001"); // Insert Method

                        if(res == true) {
                            Toast.makeText(getContext(),"Successfully Posted!", Toast.LENGTH_LONG).show();
                            clearDetails(); // Clear all input field and image view
                        } else {
                            Toast.makeText(getContext(),"Error While Posting", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Image Is Too Large!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }


    public boolean validAll()
    {
        int count = 0;
        boolean result = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        simpleDateFormat.format(new Date());


         value[0] = generateID("PST","select post_id from post_Table");
         value[1] = valid.getPostSubject(postSubject,getString(R.string.post_subject_errorMessage1),getString(R.string.post_subject_errorMessage2),100);
         value[2] = valid.getPostSubject(postdescription,getString(R.string.post_description_errorMessage1),getString(R.string.post_description_errorMessage2),300);
         value[3] = simpleDateFormat.format(new Date());
         result = valid.checkImage(postImage);

        for(int i = 0; i < value.length;i++)
        {
            if(value[i] != "")
            {
                count++;
            }
        }

        if(count == 4)
        {
            if(result)
            {
                return true;
            }
            //Toast.makeText(getContext(),getString(R.string.image_errorMessage), LENGTH_LONG).show();
            StyleableToast.makeText(getContext(),getString(R.string.post_image_errorMessage),R.style.errorToast).show();
        }
        return false;
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
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(mImageUri);
                    selectedImage = BitmapFactory.decodeStream(imageStream);
                    postImage.setImageBitmap(selectedImage);
//                    memberImage.setImageURI(mImageUri);

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

    public void onChooseFile(View v) // To choose the insert file
    {
        CropImage.activity().start(getContext(),PostSend.this);
    }

    public void clearDetails()
    {
         postSubject.getText().clear();
         postdescription.getText().clear();
         postImage.setImageDrawable(null);
    }

}
