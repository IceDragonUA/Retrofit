package com.evaluation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author Vladyslav Havrylenko
 * @since 21.03.2020
 */
public class PageViewModel extends ViewModel {

    private MutableLiveData<String> mSearch = new MutableLiveData<>();
    private MutableLiveData<Integer> mAssetId = new MutableLiveData<>();

    public void setSearchTerm(String name) {
        mSearch.setValue(name);
    }

    public LiveData<String> getSearchTerm() {
        return mSearch;
    }

    public void setAssetId(Integer name) {
        mAssetId.setValue(name);
    }

    public LiveData<Integer> getAssetId() {
        return mAssetId;
    }
}
