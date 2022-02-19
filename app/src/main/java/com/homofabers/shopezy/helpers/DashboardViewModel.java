package com.homofabers.shopezy.helpers;

import android.content.ClipData;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> selectedItem = new MutableLiveData<String>();
    public void selectItem(String item) {
        selectedItem.setValue(item);
        Log.d("Sell Card select: " , item);
    }
    public LiveData<String> getSelectedItem() {
        return selectedItem;
    }

}
