package com.example.hunterz.ui.send_notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendNotificationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SendNotificationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
