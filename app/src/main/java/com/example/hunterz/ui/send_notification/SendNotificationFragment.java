package com.example.hunterz.ui.send_notification;

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

public class SendNotificationFragment extends Fragment {

    private SendNotificationViewModel sendNotificationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendNotificationViewModel = ViewModelProviders.of(this).get(SendNotificationViewModel.class);
        View view = inflater.inflate(R.layout.fragment_send_post, container, false);


        return view;
    }
}
