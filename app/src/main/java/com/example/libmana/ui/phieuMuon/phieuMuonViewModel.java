package com.example.libmana.ui.phieuMuon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class phieuMuonViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public phieuMuonViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}