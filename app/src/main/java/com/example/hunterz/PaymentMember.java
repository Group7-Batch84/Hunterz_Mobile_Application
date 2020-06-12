package com.example.hunterz;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;


public class PaymentMember extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_member, container, false);

        progressBar=view.findViewById(R.id.progressBar1);
        recyclerView=view.findViewById(R.id.paymentMemberView);

        viewPayment();

        return view;
    }

    public void viewPayment()
    {
        // Menu List
        ArrayList<Payment> payment = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cursor res = db.getMember("SELECT * FROM payment_Table WHERE member_id='"+SignIn.MemberID+ "'");

        while (res.moveToNext())
        {

            payment.add(new Payment(res.getString(0),res.getString(1),res.getString(4),res.getString(2),
                    res.getDouble(3)));

        }
        res.close();
        // Menu List View
        RecyleViewerPayment adapterPayment = new RecyleViewerPayment(payment,"memberPay",getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(adapterPayment);
        progressBar.setVisibility(View.GONE);

    }


}
