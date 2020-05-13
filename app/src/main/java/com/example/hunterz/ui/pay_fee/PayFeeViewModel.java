package com.example.hunterz.ui.pay_fee;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PayFeeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PayFeeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}