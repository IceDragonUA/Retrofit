package com.evaluation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author Vladyslav Havrylenko
 * @since 21.03.2020
 */
public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mAssetId = new MutableLiveData<>();

    public void setName(Integer name) {
        mAssetId.setValue(name);
    }

    public LiveData<Integer> getName() {
        return mAssetId;
    }
}
