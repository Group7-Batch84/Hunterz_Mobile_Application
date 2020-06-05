package com.example.hunterz;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import java.util.ArrayList;


public class PostModify extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Post> post;
    RecycleViewerPost adapterMenu;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_modify, container, false);

        post = new ArrayList<Post>();
        recyclerView=view.findViewById(R.id.postView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
//        progressBar=view.findViewById(R.id.progressBar1);

        viewNewMember();






        return view;
    }

    public void viewNewMember() {
//        ArrayList<Member> member = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cursor res = db.getMember("SELECT * FROM post_Table");

        while (res.moveToNext()) {
            byte[] image = res.getBlob(4);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

            post.add(new Post(res.getString(0),res.getString(1),res.getString(2),res.getString(3),bmp));
        }
        res.close();

        adapterMenu = new RecycleViewerPost(post,getContext(),PostModify.this);
        recyclerView.setAdapter(adapterMenu);
//        progressBar.setVisibility(View.GONE);

    }


}
