package com.example.hunterz.ui.pay_fee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hunterz.R;

public class PayFeeFragment extends Fragment {

    private PayFeeViewModel payFeeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        payFeeViewModel = ViewModelProviders.of(this).get(PayFeeViewModel.class);

        View view = inflater.inflate(R.layout.fragment_pay_fee, container, false);
        final TextView textView = view.findViewById(R.id.text_tools);
        payFeeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        return view;
    }
}