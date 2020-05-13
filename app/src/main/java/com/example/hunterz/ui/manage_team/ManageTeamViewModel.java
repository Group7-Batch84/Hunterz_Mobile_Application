package com.example.hunterz.ui.manage_team;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageTeamViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManageTeamViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}