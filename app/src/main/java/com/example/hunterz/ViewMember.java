package com.example.hunterz;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import java.util.ArrayList;

public class ViewMember extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_member, container, false);

        progressBar=view.findViewById(R.id.progressBar1);
        recyclerView=view.findViewById(R.id.memberView);

        viewItems();

        return view;
    }


    public void viewItems()
    {
        // Menu List
        ArrayList<Member> member = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cursor res = db.getMember("SELECT * FROM member_Table,authentication_Table WHERE member_Table.authentication_id = authentication_Table.authentication_id");

        while (res.moveToNext())
        {
            byte[] image = res.getBlob(9);
            Bitmap bmp = BitmapFactory.decodeByteArray(image,0,image.length);

            member.add(new Member(res.getString(0),res.getString(1),res.getString(4),res.getString(13),
                    res.getString(5),res.getString(7),res.getString(2),res.getString(3),
                    res.getString(6), res.getString(14),res.getString(8),bmp));

        }
        res.close();
        // Menu List View
        RecycleViewerMember adapterMember = new RecycleViewerMember(member,"view",getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(adapterMember);

        progressBar.setVisibility(View.GONE);
    }


}
