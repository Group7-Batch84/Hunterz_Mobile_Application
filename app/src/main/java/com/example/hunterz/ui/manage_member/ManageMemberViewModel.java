package com.example.hunterz.ui.manage_member;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageMemberViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManageMemberViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}