package com.example.libmana.ui.loaiSach;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class loaiSachViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public loaiSachViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}