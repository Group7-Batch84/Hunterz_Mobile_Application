package com.example.hunterz.ui.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hunterz.R;
import com.example.hunterz.ui.send_post.SendPostViewModel;

public class PaymentFragment extends Fragment {

    private PaymentViewModel paymentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);

        View view = inflater.inflate(R.layout.fragment_pay_fee, container, false);

        final TextView textView = view.findViewById(R.id.text_send);
        paymentViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        return view;
    }
}
