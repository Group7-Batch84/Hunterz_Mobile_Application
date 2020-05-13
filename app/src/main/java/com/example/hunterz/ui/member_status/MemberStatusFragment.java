package com.example.hunterz.ui.member_status;

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

public class MemberStatusFragment extends Fragment {

    private MemberStatusViewModel memberStatusViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        memberStatusViewModel = ViewModelProviders.of(this).get(MemberStatusViewModel.class);
        View view = inflater.inflate(R.layout.fragment_member_status, container, false);

        final TextView textView = view.findViewById(R.id.text_slideshow);
        memberStatusViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        return view;
    }
}