package com.evaluation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author Vladyslav Havrylenko
 * @since 21.03.2020
 */
class PageViewModel : ViewModel() {

    private val mAssetId = MutableLiveData<Int>()

    fun setAssetId(name: Int) {
        mAssetId.value = name
    }

    val assetId: LiveData<Int>
        get() = mAssetId
}