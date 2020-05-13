package com.example.hunterz.ui.send_post;

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

public class SendPostFragment extends Fragment {

    private SendPostViewModel sendPostViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendPostViewModel = ViewModelProviders.of(this).get(SendPostViewModel.class);
        View view = inflater.inflate(R.layout.fragment_send_post, container, false);
        final TextView textView = view.findViewById(R.id.text_send);
        sendPostViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        return view;
    }
}