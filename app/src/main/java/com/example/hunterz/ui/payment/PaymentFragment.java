package com.example.hunterz.ui.payment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hunterz.DatabaseHandler;
import com.example.hunterz.Payment;
import com.example.hunterz.R;
import com.example.hunterz.RecycleViewerMember;
import com.example.hunterz.RecyleViewerPayment;
import com.example.hunterz.ui.send_post.SendPostViewModel;

import java.util.ArrayList;

public class PaymentFragment extends Fragment {

    private PaymentViewModel paymentViewModel;

    RecyclerView recyclerView;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);

        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        progressBar=view.findViewById(R.id.progressBar1);
        recyclerView=view.findViewById(R.id.paymentView);

        viewPayment();

        return view;
    }

    public void viewPayment()
    {
        // Menu List
        ArrayList<Payment> payment = new ArrayList<>();
        DatabaseHandler db = new DatabaseHandler(getContext());
        Cursor res = db.getMember("SELECT * FROM payment_Table");

        while (res.moveToNext())
        {
//            byte[] image = res.getBlob(9);
//            Bitmap bmp = BitmapFactory.decodeByteArray(image,0,image.length);

            payment.add(new Payment(res.getString(0),res.getString(1),res.getString(4),res.getString(2),
                    res.getDouble(3)));

        }
        res.close();
        // Menu List View
        RecyleViewerPayment adapterPayment = new RecyleViewerPayment(payment,"adminPay",getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyclerView.setAdapter(adapterPayment);

        progressBar.setVisibility(View.GONE);
    }
}
